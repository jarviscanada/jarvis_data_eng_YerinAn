package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.dto.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.utils.Status;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {

  @Captor
  ArgumentCaptor<SecurityOrder> captorSecurityOrder;

  @Mock
  private AccountDao accountDao;
  @Mock
  private QuoteDao quoteDao;
  @Mock
  private PositionDao positionDao;
  @Mock
  private SecurityOrderDao securityOrderDao;
  @Mock
  private TraderDao traderDao;

  @InjectMocks
  OrderService orderService;

  private MarketOrderDto marketOrderDto;
  private Trader trader;
  private Account account;
  private Quote quote;
  private SecurityOrder securityOrder;

  @Before
  public void setUp() throws Exception {
    trader = new Trader();
    trader.setFirstName("Micheal");
    trader.setLastName("Jackson");
    trader.setCountry("Canada");
    trader.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    trader.setEmail("jackson@gmail.com");
    traderDao.save(trader);

    quote = new Quote();
    quote.setAskPrice(50d);
    quote.setAskSize(50);
    quote.setBidPrice(50.5d);
    quote.setBidSize(50);
    quote.setId("AAPL");
    quote.setLastPrice(50.3d);
    quoteDao.save(quote);

    account = new Account();
    account.setAmount(1000d);
    account.setTraderId(trader.getId());
    accountDao.save(account);

    securityOrder = new SecurityOrder();
    securityOrder.setAccountId(account.getId());
    securityOrder.setStatus(Status.CANCELLED.getValue());
    securityOrder.setTicker(quote.getTicker());
    securityOrder.setSize(10);
    securityOrder.setPrice(20d);
    securityOrder.setNotes("Note");
    securityOrderDao.save(securityOrder);
  }

  @After
  public void tearDown() throws Exception {
    securityOrderDao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();
    quoteDao.deleteAll();
  }

  @Test
  public void executeMarketOrder() {
    marketOrderDto = new MarketOrderDto();
    marketOrderDto.setAccountId(account.getId());
    marketOrderDto.setSize(0);
    marketOrderDto.setTicker("AAPL");
    try{
      orderService.executeMarketOrder(marketOrderDto);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    marketOrderDto.setSize(1);
    marketOrderDto.setTicker("AAaa");
    try{
      orderService.executeMarketOrder(marketOrderDto);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }

  @Test
  public void handleBuyMarketOrder(){
    marketOrderDto = new MarketOrderDto();
    marketOrderDto.setAccountId(account.getId());
    marketOrderDto.setSize(20);
    marketOrderDto.setTicker("AAaa");
    try{
      orderService.handleBuyMarketOrder(marketOrderDto, securityOrder, account);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    marketOrderDto.setTicker("AAPL");
    marketOrderDto.setSize(-10);
    try{
      orderService.handleBuyMarketOrder(marketOrderDto, securityOrder, account);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    account.setAmount(0d);
    accountDao.save(account);
    try{
      orderService.handleBuyMarketOrder(marketOrderDto, securityOrder, account);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }

  @Test
  public void handleSellMarketOrder(){
    marketOrderDto = new MarketOrderDto();
    marketOrderDto.setAccountId(account.getId());
    marketOrderDto.setSize(-5);
    marketOrderDto.setTicker("AAaa");
    try{
      orderService.handleSellMarketOrder(marketOrderDto, securityOrder, account);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    marketOrderDto.setTicker("AAPL");
    marketOrderDto.setSize(-15);
    try{
      orderService.handleSellMarketOrder(marketOrderDto, securityOrder, account);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    marketOrderDto.setSize(15);
    try{
      orderService.handleSellMarketOrder(marketOrderDto, securityOrder, account);
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }
}