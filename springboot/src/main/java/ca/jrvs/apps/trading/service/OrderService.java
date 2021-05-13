package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dto.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.utils.Status;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private QuoteDao quoteDao;
  private PositionDao positionDao;

  @Autowired
  public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
      QuoteDao quoteDao, PositionDao positionDao){
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.quoteDao = quoteDao;
    this.positionDao = positionDao;
  }

  /**
   * Execute a market order
   *
   * -validate the order(e.g. size, and ticket)
   * -create a securityOrder ( for security_order table)
   * -Handle buy or sell order
   *    -buy order : check account balance (calls helper method)
   *    -sell order : check position for the ticker/symbol (calls helper method)
   *    -(please don't forget to update securityOrder.status)
   * -Save and return securityOrder
   *
   * NOTE: you will need to some helper methods (protected or private)
   * @param orderDto market order
   * @return SecurityOrder from security_order table
   * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException for invalid input
   */
  public SecurityOrder executeMarketOrder(MarketOrderDto orderDto){
    validateOrderDto(orderDto);
    Account account = null;
    SecurityOrder securityOrder = null;
    try{
      account = accountDao.findByTraderId(orderDto.getAccountId()).get();
    }catch (DataAccessException e){
      logger.error("ERROR: INVALID ACCOUNT ID" + orderDto.getAccountId());
    }
    securityOrder = new SecurityOrder();
    securityOrder.setAccountId(account.getId());
    securityOrder.setTicker(orderDto.getTicker());
    if(orderDto.getSize() > 0)
      handleBuyMarketOrder(orderDto, securityOrder, account);
    else
      handleSellMarketOrder(orderDto, securityOrder, account);
    return securityOrder;
  }

  /**
   * Check orderDto value
   * - if dto is null or accountId is null, throw error
   * - if ticker is not exist, throw error
   * - if order size is 0, throw error
   * @param orderDto
   */
  private void validateOrderDto(MarketOrderDto orderDto){
    if(orderDto == null || orderDto.getAccountId() == null ||
        orderDto.getTicker() == null || orderDto.getSize() == null)
      throw new IllegalArgumentException("ERROR: INVALID MARKET ORDER");
    if(!quoteDao.existsById(orderDto.getTicker()))
      throw new DataRetrievalFailureException("ERROR: INVALID TICKER SYMBOL");
    if(orderDto.getSize() == 0)
      throw new IllegalArgumentException("ERROR: INVALID ORDER SIZE");
  }

  /**
   * Helper method that execute a buy order
   * @param marketOrderDto user order
   * @param securityOrder to be saved in data database
   * @param account account
   */
  protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account){
    Optional<Quote> quote = quoteDao.findById(marketOrderDto.getTicker());
    invalidMarketOrder(true, quote, account, marketOrderDto);
    Double total = quote.get().getBidPrice() * marketOrderDto.getSize();
    if(total < account.getAmount()){//success
      account.setAmount(account.getAmount() - total);
      accountDao.updateOne(account);
      securityOrder.setSize(marketOrderDto.getSize());
      securityOrder.setPrice(quote.get().getBidPrice());
      securityOrder.setStatus(Status.FILLED.getValue());
      securityOrder.setNotes("SUCCESS-BUY TIME: " + DateFormat.getDateTimeInstance().format(new Date(0)));
    }else {//cancel
      securityOrder.setStatus(Status.CANCELLED.getValue());
      securityOrder.setNotes("ERROR: INSUFFICIENT FUNDS");
    }
    securityOrderDao.save(securityOrder);
  }

  /**
   * Helper method that execute a sell order
   * @param marketOrderDto user order
   * @param securityOrder to be saved in data database
   * @param account account
   */
  protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account){
    Optional<Quote> quote = quoteDao.findById(marketOrderDto.getTicker());
    invalidMarketOrder(false, quote, account, marketOrderDto);
    Double total = quote.get().getAskPrice() * marketOrderDto.getSize();
    account.setAmount(account.getAmount() + total);
    accountDao.updateOne(account);
    securityOrder.setStatus(Status.FILLED.getValue());
    securityOrder.setPrice(quote.get().getAskPrice());
    securityOrder.setSize(marketOrderDto.getSize());
    securityOrder.setNotes("SUCCESS-SELL TIME " + DateFormat.getDateTimeInstance().format(new Date(0)));
    securityOrderDao.save(securityOrder);
  }

  /**
   * 1. check quote is present: if quote does not exist, throw error
   * 2. buy(Boolean isBuy == true)
   *    - check account amount: if amount is 0, throw error
   *    - check order size: if size is negative value, throw error
   * 3. sell(Boolean isBuy == false)
   *    - check position exists: if position does not exist, throw error
   *    - check order size: if size is positive value, throw error
   *    - check security order size: if security order size is less than request order size, throw error
   */
  private void invalidMarketOrder(Boolean isBuy, Optional<Quote> quote, Account account, MarketOrderDto orderDto){
    if(!quote.isPresent())
      throw new IllegalArgumentException("ERROR: INVALID TICKER");
    if(isBuy){ //Buy Market Order
      if(account.getAmount() <= 0)
        throw new IllegalArgumentException("ERROR: INSUFFICIENT FUNDS");
      if(orderDto.getSize() <= 0)
        throw new IllegalArgumentException("ERROR: INVALID BUYING");
    }else{ //sell Market order
      if(!positionDao.existsById(account.getId()))
        throw new IllegalArgumentException("ERROR: CHECK YOUR POSITION");
      List<SecurityOrder> sOrder = securityOrderDao.findByAccountId(account.getId(), orderDto.getTicker());
      if(orderDto.getSize() >= 0)
        throw new IllegalArgumentException("ERROR: INVALID SELLING");
      Integer size = 0;
      for(SecurityOrder s : sOrder){
        size += s.getSize();
      }
      if(size < Math.abs(orderDto.getSize()))
        throw new IllegalArgumentException("ERROR: EXCEED SELLING SIZE");
    }
  }
}
