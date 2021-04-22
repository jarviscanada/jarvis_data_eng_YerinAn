package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {
  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao){
    this.marketDataDao = marketDataDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Update quote table against IEX source
   * -get all quotes from the DB
   * -foreach ticker get iexQuote
   * -convert iexQuote to quote entity
   * -persist quote to DB
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public void updateMarketData(){
    List<Quote> quoteList = quoteDao.findAll();
    quoteList.forEach(q -> quoteDao.save(buildQuoteFromIexQuote(marketDataDao.findById(q.getTicker()).get())));
  }

  /**
   * Helper method. Map a IexQuote to a Quote entity
   * Note: iexQuote.getLastPrice() == null if the stock market is closed
   * Make sure set a default value for number fields.
   * @param iexQuote
   * @return
   */
  public static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
    String ticker = iexQuote.getSymbol();
    Quote quote = new Quote();
    if(ticker == null)
      throw new IllegalArgumentException("ERROR: INVALID TICKER");
    Double askPrice = iexQuote.getIexAskPrice();
    Long askSize = iexQuote.getIexAskSize();
    Double lastPrice = iexQuote.getLatestPrice();
    if(lastPrice == null)
      throw new IllegalArgumentException("ERROR: MARKET IS CLOSED");
    Double bidPrice = iexQuote.getIexBidPrice();
    Long bidSize = iexQuote.getIexBidSize();

    quote.setTicker(ticker);
    quote.setAskPrice((askPrice !=null) ? askPrice : 0);
    quote.setAskSize((askSize != null) ? askSize.intValue() : 0);
    quote.setLastPrice((lastPrice !=null) ? lastPrice : 0);
    quote.setBidPrice((bidPrice != null) ? bidPrice:0);
    quote.setBidSize((bidSize !=null) ? bidSize.intValue() : 0);
    return quote;
  }

  /**
   * Validate (aganist IEX) and save given tickers to quote table.
   * -Get iexQuote(s)
   * -Convert each iexQuote to Quote entity
   * -Persist the quote to DB
   *
   * @param tickers a list of tickers/symbols
   * @throws IllegalArgumentException if ticker is not found from IEX
   */
  public List<Quote> saveQuote(List<String> tickers){
    List<Quote> quoteList = new ArrayList<>();
    tickers.forEach(item -> quoteList.add(saveQuote(item)));
    return quoteList;
  }

  /**
   * Helper method
   * @param ticker
   */
  public Quote saveQuote(String ticker){
    Quote quote = buildQuoteFromIexQuote(findIexQuoteByTicker(ticker));
    return saveQuote(quote);
  }

  /**
   * Find an IexQuote
   * @param ticker id
   * @return IexQuote object
   * @throws IllegalArgumentException if ticker is invalid
   */
  public IexQuote findIexQuoteByTicker(String ticker){
    return marketDataDao.findById(ticker)
        .orElseThrow(()-> new IllegalArgumentException(ticker + "is invalid"));
  }

  /**
   * Update a given quote to quote table without validation
   * @param quote entity
   */
  public Quote saveQuote(Quote quote){
    return quoteDao.save(quote);
  }

  /**
   * Find all quotes from the quote table
   * @return a list of quotes
   */
  public List<Quote> findAllQuotes(){
    return quoteDao.findAll();
  }
}