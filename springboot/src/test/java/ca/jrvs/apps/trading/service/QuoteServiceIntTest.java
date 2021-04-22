package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setup(){ quoteDao.deleteAll(); }

  @Test
  public void T010_findIexQuoteByTicker() {
    IexQuote iexQuote = quoteService.findIexQuoteByTicker("AAPL");
    assertEquals("AAPL", iexQuote.getSymbol());
  }

  @Test
  public void T020_saveQuote() {
    String ticker = "AMD";
    Quote quote = quoteService.saveQuote(ticker);
    assertTrue(quoteDao.findById(ticker).isPresent());
    assertEquals(ticker, quote.getTicker());
  }

  @Test
  public void T030_saveQuotes() {
    List<Quote> quoteList = quoteService.saveQuote(Arrays.asList("AAPL", "FB"));
    assertEquals(2, quoteList.size());
  }

  @Test
  public void T040_updateMarketData() {
    Quote quote = new Quote();
    quote.setTicker("AAPL");
    quote.setBidSize(20);
    quote.setBidPrice(25d);
    quote.setAskSize(20);
    quote.setAskPrice(21d);
    quote.setLastPrice(23d);
    quoteService.saveQuote(quote);
    quoteService.updateMarketData();
    Quote result = quoteDao.findById(quote.getId()).get();
    assertEquals(quote.getTicker(), result.getTicker());
  }

  @Test
  public void T050_findAllQuotes() {
    Quote quote = new Quote();
    quote.setTicker("GOOGL");
    quote.setBidSize(20);
    quote.setBidPrice(55d);
    quote.setAskSize(20);
    quote.setAskPrice(56d);
    quote.setLastPrice(57d);
    quoteService.saveQuote(quote);
    Quote quote1 = new Quote();
    quote1.setTicker("AAPL");
    quote1.setBidSize(20);
    quote1.setBidPrice(25d);
    quote1.setAskSize(20);
    quote1.setAskPrice(21d);
    quote1.setLastPrice(23d);
    quoteService.saveQuote(quote1);
    List<Quote> quoteList = quoteService.findAllQuotes();
    assertEquals(2, quoteList.size());
    assertEquals(quote.getTicker(), quoteList.get(0).getTicker());
    assertEquals(quote1.getTicker(), quoteList.get(1).getTicker());
  }
}