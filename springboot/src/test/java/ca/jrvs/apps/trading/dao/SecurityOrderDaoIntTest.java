package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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
public class SecurityOrderDaoIntTest {

  @Autowired
  private SecurityOrderDao securityOrderDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;
  @Autowired
  private QuoteDao quoteDao;

  private SecurityOrder securityOrder;
  private Account account;
  private Trader trader;
  private Quote quote;

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
    account.setAmount(5000.5d);
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
    quoteDao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();
  }

  @Test
  public void T010_findAllById(){
    Optional<SecurityOrder> result = securityOrderDao.findById(securityOrder.getId());
    assertTrue(result.isPresent());
    assertEquals(securityOrder.getAccountId(), result.get().getAccountId());
  }

  @Test
  public void T020_count() {
    assertEquals(1, securityOrderDao.count());
  }

  @Test
  public void T030_existById() {
    assertTrue(securityOrderDao.existsById(securityOrder.getId()));
  }

  @Test
  public void T040_save() {
    SecurityOrder test = new SecurityOrder();
    test.setAccountId(account.getId());
    test.setStatus(Status.FILLED.getValue());
    test.setTicker(quote.getTicker());
    test.setSize(20);
    test.setPrice(30d);
    test.setNotes("SAVE");
    SecurityOrder result = securityOrderDao.save(test);
    assertEquals(test.getId(), result.getId());
  }

  @Test
  public void T050_saveAll() throws Exception{
    Quote quote1 = new Quote();
    quote1.setAskPrice(30d);
    quote1.setAskSize(30);
    quote1.setBidPrice(30.5d);
    quote1.setBidSize(30);
    quote1.setId("FB");
    quote1.setLastPrice(30.3d);
    quoteDao.save(quote1);

    Trader trader1 = new Trader();
    trader1.setFirstName("Mickey");
    trader1.setLastName("Mouse");
    trader1.setCountry("USA");
    trader1.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("2000-09-09"));
    trader1.setEmail("disney@gmail.com");
    traderDao.save(trader1);

    Account account1 = new Account();
    account1.setAmount(5000.5d);
    account1.setTraderId(trader1.getId());
    accountDao.save(account1);

    SecurityOrder test = new SecurityOrder();
    test.setAccountId(account1.getId());
    test.setStatus(Status.FILLED.getValue());
    test.setTicker(quote1.getTicker());
    test.setSize(20);
    test.setPrice(30d);
    test.setNotes("SAVE");

    List<SecurityOrder> results = (List<SecurityOrder>) securityOrderDao.saveAll(Arrays.asList(test));
    assertEquals(test.getId(), results.get(0).getId());
    assertEquals(test.getTicker(), results.get(0).getTicker());
  }

  @Test
  public void T060_updateOne() {
    Account test = new Account();
    test.setAmount(3000.5d);
    test.setTraderId(trader.getId());
    accountDao.save(test);
    securityOrder.setAccountId(test.getId());
    securityOrder.setStatus(Status.FILLED.getValue());
    securityOrder.setTicker(quote.getTicker());
    securityOrder.setSize(10);
    securityOrder.setPrice(20d);
    securityOrder.setNotes("update Date");
    securityOrderDao.updateOne(securityOrder);
    Optional<SecurityOrder> result = securityOrderDao.findById(securityOrder.getId());
    assertTrue(result.isPresent());
    assertEquals(securityOrder.getStatus(), result.get().getStatus());
    assertEquals(securityOrder.getAccountId(), result.get().getAccountId());
    assertEquals(securityOrder.getNotes(), result.get().getNotes());
  }

  @Test
  public void T070_deleteById() {
    securityOrderDao.deleteById(securityOrder.getId());
    assertEquals(0, securityOrderDao.count());
  }
}