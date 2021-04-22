package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.text.SimpleDateFormat;
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
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

  private Trader savedTrader;

  @Before
  public void setUp() throws Exception {
    savedTrader = new Trader();
    savedTrader.setFirstName("Micheal");
    savedTrader.setLastName("Jackson");
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    savedTrader.setEmail("jackson@gmail.com");
    traderDao.save(savedTrader);
  }

  @After
  public void tearDown() throws Exception {
    traderDao.deleteAll();
  }

  @Test
  public void T010_findAllById(){
    List<Trader> traders = traderDao.findAllById(Arrays.asList(savedTrader.getId()));
    assertEquals(1, traders.size());
    assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
  }

  @Test
  public void T020_count(){
    assertEquals(1, traderDao.count());
  }

  @Test
  public void T030_existById(){
    assertTrue(traderDao.existsById(savedTrader.getId()));
  }

  @Test
  public void T040_save() throws Exception {
    Trader trader = new Trader();
    trader.setFirstName("Kanye");
    trader.setLastName("West");
    trader.setCountry("USA");
    trader.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    trader.setEmail("kanye@gmail.com");
    Trader result = traderDao.save(trader);
    assertEquals(trader, result);
  }

  @Test
  public void T050_saveAll() throws Exception {
    Trader trader = new Trader();
    trader.setFirstName("Kanye");
    trader.setLastName("West");
    trader.setCountry("USA");
    trader.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    trader.setEmail("kanye@gmail.com");

    Trader trader1 = new Trader();
    trader1.setFirstName("Mickey");
    trader1.setLastName("Mouse");
    trader1.setCountry("USA");
    trader1.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    trader1.setEmail("disney@gmail.com");

    List<Trader> results = (List<Trader>) traderDao.saveAll(Arrays.asList(trader, trader1));
    assertEquals(2, results.size());
    assertEquals("kanye@gmail.com", results.get(0).getEmail());
    assertEquals("disney@gmail.com", results.get(1).getEmail());
  }

  @Test
  public void T060_deleteById() {
    traderDao.deleteById(savedTrader.getId());
    assertEquals(0, traderDao.count());
  }
}