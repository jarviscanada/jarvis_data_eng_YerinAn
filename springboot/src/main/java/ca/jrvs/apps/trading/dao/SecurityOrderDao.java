package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.utils.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder>{
  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);
  private final String TABLE_NAME = "security_order";
  private final String ID_COLUMN = "id";
  private final String ACCOUNT_ID_COLUMN = "account_id";
  private final String STATUS_FILED = Status.FILLED.getValue();

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public SecurityOrderDao(DataSource dataSource){
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
  Class<SecurityOrder> getEntityClass() {
    return SecurityOrder.class;
  }

  @Override
  public int updateOne(SecurityOrder entity) {
    String query="UPDATE " + TABLE_NAME +
        " SET account_id=?, status=?, ticker=?, size=?, price=?, notes=? WHERE " + ID_COLUMN + "=?";
    return jdbcTemplate.update(query, entity.getAccountId(), entity.getStatus(), entity.getTicker(),
        entity.getSize(), entity.getPrice(), entity.getNotes(), entity.getId());
  }

  @Override
  public void delete(SecurityOrder entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends SecurityOrder> entities) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public void deleteByAccountId(Integer accountId){
    String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ACCOUNT_ID_COLUMN + " =?";
    try{
      getJdbcTemplate().update(query, accountId);
    }catch (DataRetrievalFailureException e){
      logger.error("ERROR: FAILED TO DELETE" + accountId);
    }
  }

  public List<SecurityOrder> findByAccountId(Integer accountId, String ticker){
    String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ACCOUNT_ID_COLUMN + " =? AND " +
        "ticker = ? AND status = " + STATUS_FILED;
    List<SecurityOrder> entity = new ArrayList<>();
    try{
      entity = getJdbcTemplate().query(query, BeanPropertyRowMapper.newInstance(getEntityClass()), accountId, ticker);
    } catch (IncorrectResultSizeDataAccessException e){
      logger.debug("Can't find account id/quote id:", e);
    }
    return entity;
  }
}
