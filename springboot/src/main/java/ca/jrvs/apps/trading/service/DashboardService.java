package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.List;
import java.util.Optional;
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
    validTraderId(traderId);
    Optional<Trader> trader = traderDao.findById(traderId);
    Optional<Account> account = accountDao.findByTraderId(traderId);
    return new TraderAccountView(trader.get(), account.get());
  }

  public void validTraderId(Integer traderId){
    if(traderId == null || traderId <= 0)
      throw new IllegalArgumentException("ERROR: INVALID TRADER ID");
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
    validTraderId(traderId);
    List<Position> positions = null;
    Optional<Account> account = accountDao.findByTraderId(traderId);
    if(account.isPresent())
      positions = positionDao.findById(account.get().getId());
    return new PortfolioView(positions, account.get());
  }

  /**
   * @throws IllegalArgumentException if trader id is not found
   */
  private Account findAccountByTraderId(Integer traderId){
    validTraderId(traderId);
    return accountDao.findByTraderId(traderId).get();
  }
}
