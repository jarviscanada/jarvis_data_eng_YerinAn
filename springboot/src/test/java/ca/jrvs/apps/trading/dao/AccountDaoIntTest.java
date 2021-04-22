package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
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
public class AccountDaoIntTest {

  @Autowired
  AccountDao accountDao;
  @Autowired
  TraderDao traderDao;

  Account account;
  Trader trader;

  @Before
  public void setUp() throws Exception {
    trader = new Trader();
    trader.setFirstName("Micheal");
    trader.setLastName("Jackson");
    trader.setCountry("Canada");
    trader.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    trader.setEmail("jackson@gmail.com");
    traderDao.save(trader);

    account = new Account();
    account.setAmount(50.5d);
    account.setTraderId(trader.getId());
    accountDao.save(account);
  }

  @After
  public void tearDown() throws Exception {
    accountDao.deleteAll();
    traderDao.deleteAll();
  }

  @Test
  public void T010_findAllById(){
    Account account1 = new Account();
    account1.setAmount(50.5d);
    account1.setTraderId(trader.getId());
    accountDao.save(account1);
    List<Account> accountList = accountDao.findAllById(Arrays.asList(account.getId(), account1.getId()));
    assertEquals(account.getId(), accountList.get(0).getId());
    assertEquals(account1.getId(), accountList.get(1).getId());
  }

  @Test
  public void T020_count() {
    assertEquals(1, accountDao.count());
  }

  @Test
  public void T030_existById() {
    assertTrue(accountDao.existsById(account.getId()));
  }

  @Test
  public void T040_save() {
    Account test = new Account();
    test.setAmount(50.5d);
    test.setTraderId(trader.getId());
    Account result = accountDao.save(test);
    assertEquals(test.getId(), result.getId());
    assertEquals(test.getAmount(), result.getAmount());
  }

  @Test
  public void T050_saveAll() throws Exception{
    Trader trader1 = new Trader();
    trader1.setFirstName("Harry");
    trader1.setLastName("Potter");
    trader1.setCountry("UK");
    trader1.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1995-07-09"));
    trader1.setEmail("harry@gmail.com");
    traderDao.save(trader1);

    Account account1 = new Account();
    account1.setAmount(1000d);
    account1.setTraderId(trader.getId());
    Account account2 = new Account();
    account2.setAmount(1500.3d);
    account2.setTraderId(trader1.getId());
    List<Account> accountList = (List<Account>) accountDao.saveAll(Arrays.asList(account1, account2));
    assertEquals(account1.getAmount(), accountList.get(0).getAmount());
    assertEquals(account1.getTraderId(), accountList.get(0).getTraderId());
    assertEquals(account2.getAmount(), accountList.get(1).getAmount());
    assertEquals(account2.getTraderId(), accountList.get(1).getTraderId());
  }

  @Test
  public void T060_updateOne() throws Exception{
    Trader trader1 = new Trader();
    trader1.setFirstName("Harry");
    trader1.setLastName("Potter");
    trader1.setCountry("UK");
    trader1.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1995-07-09"));
    trader1.setEmail("harry@gmail.com");
    traderDao.save(trader1);

    account.setAmount(5000d);
    accountDao.updateOne(account);
    Optional<Account> result = accountDao.findById(account.getId());
    assertTrue(result.isPresent());
    assertEquals(account.getTraderId(), result.get().getTraderId());
    assertEquals(account.getAmount(), result.get().getAmount());
  }

  @Test
  public void T070_deleteById() {
    accountDao.deleteById(account.getId());
    assertEquals(0, accountDao.count());
  }
}