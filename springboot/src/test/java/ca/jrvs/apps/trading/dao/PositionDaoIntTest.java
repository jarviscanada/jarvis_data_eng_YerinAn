package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.utils.Status;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
public class PositionDaoIntTest {

  @Autowired
  private PositionDao positionDao;
  @Autowired
  private SecurityOrderDao securityOrderDao;
  @Autowired
  private QuoteDao quoteDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;

  private Position position;
  private SecurityOrder securityOrder;
  private Trader trader;
  private Quote quote;
  private Account account;

  @Before
  public void setUp() throws Exception {
    quote = new Quote();
    quote.setAskPrice(50d);
    quote.setAskSize(50);
    quote.setBidPrice(50.5d);
    quote.setBidSize(50);
    quote.setId("AAPL");
    quote.setLastPrice(50.3d);
    quoteDao.save(quote);

    trader = new Trader();
    trader.setFirstName("Kanye");
    trader.setLastName("West");
    trader.setCountry("USA");
    trader.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    trader.setEmail("kanye@gmail.com");
    traderDao.save(trader);

    account = new Account();
    account.setAmount(50.5d);
    account.setTraderId(trader.getId());
    accountDao.save(account);

    securityOrder = new SecurityOrder();
    securityOrder.setAccountId(account.getId());
    securityOrder.setStatus(Status.FILLED.getValue());
    securityOrder.setTicker(quote.getTicker());
    securityOrder.setSize(10);
    securityOrder.setPrice(20d);
    securityOrder.setNotes("Note");
    securityOrderDao.save(securityOrder);
  }

  @After
  public void tearDown() throws Exception {
    securityOrderDao.deleteAll();
    quoteDao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();
  }

  @Test
  public void T010_findById(){
    Optional<Position> result = positionDao.findById(securityOrder.getAccountId());
    assertTrue(result.isPresent());
    assertEquals(account.getId(), result.get().getAccountId());
  }

  @Test
  public void T020_findAllById(){
    List<Position> results = positionDao.findAllById(Arrays.asList(securityOrder.getAccountId()));
    assertEquals(securityOrder.getAccountId(), results.get(0).getAccountId());
  }

  @Test
  public void T030_findAll(){
    List<Position>  results = positionDao.findAll();
    assertEquals(1, results.size());
  }

  @Test
  public void T040_count(){
    assertEquals(1, positionDao.count());
  }

  @Test
  public void T050_existsById(){
    assertTrue(positionDao.existsById(securityOrder.getAccountId()));
  }

}