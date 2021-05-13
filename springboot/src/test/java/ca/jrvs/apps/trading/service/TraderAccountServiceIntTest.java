package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.text.SimpleDateFormat;
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
public class TraderAccountServiceIntTest {

  @Autowired
  private TraderAccountService traderAccountService;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;
  @Autowired
  private SecurityOrderDao securityOrderDao;

  private TraderAccountView traderAccountView;

  @Before
  public void setUp() throws Exception {
    Trader trader = new Trader();
    trader.setFirstName("Micheal");
    trader.setLastName("Jackson");
    trader.setCountry("Canada");
    trader.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1990-09-09"));
    trader.setEmail("jackson@gmail.com");
    traderAccountView = traderAccountService.createTraderAndAccount(trader);
  }

  @After
  public void tearDown() throws Exception {
    securityOrderDao.deleteByAccountId(traderAccountView.getAccount().getId());
    accountDao.deleteById(traderAccountView.getAccount().getId());
    traderDao.deleteById(traderAccountView.getTrader().getId());
  }

  @Test
  public void createTraderAndAccount() {
    Optional<Trader> test_t = traderDao.findById(traderAccountView.getTrader().getId());
    assertTrue(test_t.isPresent());
    assertEquals(test_t.get().getId(), traderAccountView.getTrader().getId());
    Optional<Account> test_a = accountDao.findById(traderAccountView.getAccount().getId());
    assertTrue(test_a.isPresent());
    assertEquals(test_a.get().getId(), traderAccountView.getAccount().getId());
  }

  @Test
  public void deleteTraderById() {
    Integer traderId = traderAccountView.getTrader().getId();
    Integer accountId = traderAccountView.getAccount().getId();
    traderAccountService.deleteTraderById(traderId);
    assertTrue(traderDao.existsById(traderId)==false);
    assertTrue(accountDao.existsById(accountId)==false);
  }

  @Test
  public void deposit() {
    try {
      traderAccountService.deposit(-1, 100d);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      traderAccountService.deposit(traderAccountView.getTrader().getId(), -100d);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    Double previousAccount = traderAccountView.getAccount().getAmount();
    Double amount = 100d;
    Double finalAccount = previousAccount + amount;
    Account account = traderAccountService.deposit(traderAccountView.getTrader().getId(), amount);
    assertEquals(finalAccount, account.getAmount());
  }

  @Test
  public void withdraw() {
    try {
      traderAccountService.deposit(-1, 100d);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      traderAccountService.deposit(traderAccountView.getTrader().getId(), -100d);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    Account account = traderAccountView.getAccount();
    account.setAmount(1000d);
    accountDao.updateOne(account);
    Double previousAccount = traderAccountView.getAccount().getAmount();
    Double amount = 100d;
    Double finalAmount = previousAccount - amount;
    Double result = traderAccountService.withdraw(traderAccountView.getTrader().getId(), amount).getAmount();
    assertEquals(finalAmount, result);
  }
}