package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;

public class PortfolioView {
  private List<Position> positions;
  private Account account;

  public PortfolioView(List<Position> positions, Account account){
    this.positions = positions;
    this.account = account;
  }

  public List<Position> getPosition() {
    return positions;
  }

  public void setPosition(List<Position> positions) {
    this.positions = positions;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
