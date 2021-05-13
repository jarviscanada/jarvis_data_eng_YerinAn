package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.utils.Status;
import java.text.SimpleDateFormat;
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
public class DashboardServiceIntTest {

  @Autowired
  private PositionDao positionDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;
  @Autowired
  private QuoteDao quoteDao;
  @Autowired
  private SecurityOrderDao securityOrderDao;
  @Autowired
  private DashboardService dashboardService;

  private Trader trader;
  private Quote quote;
  private Account account;
  private SecurityOrder securityOrder;
  private Position position;
  private TraderAccountView traderAccountView;
  private PortfolioView portfolioView;

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
  public void getTraderAccount() {
    traderAccountView = dashboardService.getTraderAccount(trader.getId());
    assertEquals(trader.getId(), traderAccountView.getTrader().getId());
    assertEquals(account.getAmount(), traderAccountView.getAccount().getAmount());
  }

  @Test
  public void getProfileViewByTraderId() {
    portfolioView = dashboardService.getProfileViewByTraderId(trader.getId());
    assertEquals(account.getId(), portfolioView.getAccount().getId());
  }
}