package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DashboardService {
  private TraderDao traderDao;
  private PositionDao positionDao;
  private AccountDao accountDao;
  private QuoteDao quoteDao;

  @Autowired
  public DashboardService(TraderDao traderDao, PositionDao positionDao,
      AccountDao accountDao, QuoteDao quoteDao){
    this.traderDao = traderDao;
    this.positionDao = positionDao;
    this.accountDao = accountDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Create and return a traderAccountView by Trader Id
   * -get trader account by id
   * -get trader info by id
   * -create and return a traderAccountView
   * @param traderId must not be null
   * @return traderAccountView
   * @throws IllegalArgumentException if traderId is null or not found
   */
  public TraderAccountView getTraderAccount(Integer traderId){

  }

  /**
   * Create and return portfolioView by trader id
   * -get account by traderId
   * -get position by accountId
   * -create and return portfolioView
   * @param traderId must not be null
   * @return portfolioView
   * @throws IllegalArgumentException if trader id is null or not found
   */
  public PortfolioView getProfileViewByTraderId(Integer traderId){

  }

  /**
   * @throws IllegalArgumentException if trader id is not found
   */
  private Account findAccountByTraderId(Integer traderId){

  }
}
