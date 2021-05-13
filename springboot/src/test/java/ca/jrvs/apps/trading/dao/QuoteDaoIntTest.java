package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
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
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote quote;

  @Before
  public void insertInfo() throws Exception{
    quote = new Quote();
    quote.setAskPrice(50d);
    quote.setAskSize(50);
    quote.setBidPrice(50.5d);
    quote.setBidSize(50);
    quote.setId("AAPL");
    quote.setLastPrice(50.3d);
    quoteDao.save(quote);
  }

  @After
  public void deleteInfo(){
    quoteDao.deleteAll();
  }

  @Test
  public void T010_save() {
    Quote test = new Quote();
    test.setAskPrice(50d);
    test.setAskSize(50);
    test.setBidPrice(50.5d);
    test.setBidSize(50);
    test.setId("MSFT");
    test.setLastPrice(50.3d);
    Quote result = quoteDao.save(test);
    assertEquals(test, result);
  }

  @Test
  public void T020_saveAll() {
    quote.setAskPrice(40d);
    quote.setAskSize(40);
    quote.setBidPrice(40.5d);
    quote.setBidSize(40);
    quote.setLastPrice(40.3d);

    Quote test = new Quote();
    test.setAskPrice(50d);
    test.setAskSize(50);
    test.setBidPrice(50.5d);
    test.setBidSize(50);
    test.setId("MSFT");
    test.setLastPrice(50.3d);

    List<Quote> quoteList = (List<Quote>) quoteDao.saveAll(Arrays.asList(quote, test));
    Quote result = quoteList.get(0);
    Quote result1 = quoteList.get(1);
    assertEquals(quote, result);
    assertEquals(test, result1);
  }

  @Test
  public void T030_findById() {
    Quote result = quoteDao.findById(quote.getId()).get();
    assertEquals(quote.getTicker(), result.getTicker());
  }

  @Test
  public void T040_existsById() {
    assertTrue(quoteDao.existsById("AAPL"));
  }

  @Test
  public void T050_findAll() {
    List<Quote> result = quoteDao.findAll();
    assertEquals(1, result.size());
    assertEquals(quote.getTicker(), result.get(0).getTicker());
  }


  @Test
  public void T060_count() {
    assertEquals(1, quoteDao.count());
  }

  @Test
  public void T070_deleteById() {
    quoteDao.deleteById(quote.getId());
    assertEquals(0, quoteDao.count());
  }

  @Test
  public void T080_deleteAll() {
    quoteDao.deleteAll();
    assertEquals(0, quoteDao.count());
  }
}