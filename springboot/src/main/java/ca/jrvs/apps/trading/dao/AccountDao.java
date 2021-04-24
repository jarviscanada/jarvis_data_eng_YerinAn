package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account>{

  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);
  private final String TABLE_NAME = "account";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public AccountDao(DataSource dataSource){
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  @Override
  Class<Account> getEntityClass() {
    return Account.class;
  }

  @Override
  public int updateOne(Account entity) {
    String query="UPDATE " + TABLE_NAME + " SET amount=? WHERE " + ID_COLUMN + "=?";
    return jdbcTemplate.update(query, entity.getAmount(), entity.getId());
  }

  @Override
  public void delete(Account entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Account> entities) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public Optional<Account> findByTraderId(Integer traderId){
    String query = "SELECT * FROM " + TABLE_NAME + " WHERE trader_id =?";
    Optional<Account> account = null;
    try{
      account = Optional.ofNullable(getJdbcTemplate().queryForObject(query, BeanPropertyRowMapper.newInstance(getEntityClass()), traderId));
    }catch (IncorrectResultSizeDataAccessException e){
      logger.debug("Can't find trader id:" + traderId, e);
    }
    return account;
  }
}
