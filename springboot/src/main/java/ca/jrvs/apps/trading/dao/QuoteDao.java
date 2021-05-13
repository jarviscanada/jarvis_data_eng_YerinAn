package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource){
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  @Override
  public Quote save(Quote quote) {
    if(existsById(quote.getTicker())){
      int updatedRowNo = updateOne(quote);
      if(updatedRowNo != 1)
        throw new DataRetrievalFailureException("Unable to update quote");
    }
    else
      addOne(quote);
    return quote;
  }

  /**
   * Helper method that saves one quote
   * @param quote
   */
  private void addOne(Quote quote){
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int row = simpleJdbcInsert.execute(parameterSource);
    if(row != 1){
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
    }
  }

  /**
   * helper method that makes sql update values objects
   * @param quote to be updated
   * @return UPDATE_SQL values
   */
  private Object[] makeUpdateValues(Quote quote){
    return new Object[]{quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(), quote.getAskPrice(), quote.getAskSize(), quote.getTicker()};
  }

  private int updateOne(Quote quote){
    String update_sql = "UPDATE quote SET last_price=?, bid_price=?, "
        + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
    return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
  }

  /**
   * Saves all given entities.
   *
   * @param entities must not be {@literal null}.
   * @return the saved entities will never be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> entities) {
    if(checkParams(entities)){
      for(S entity : entities){
        if(entity == null)
          throw new IllegalArgumentException("ERROR: ENTITY HAS NULL VALUE");
        save(entity);
      }
      return entities;
    }
    throw new RuntimeException("ERROR: CANNOT SAVE VALUE");
  }

  /**
   * Retrieves an entity by its id.
   *
   * @param s must not be {@literal null}.
   * @return the entity with the given id or {@literal Optional#empty()} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public Optional<Quote> findById(String s) {
    Quote quote = null;
    if(checkParams(s)){
      try{
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ? LIMIT 1";
        quote = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Quote.class), s);
      }catch (EmptyResultDataAccessException e){
        logger.error("ERROR: NO ENTRY WITH " + s , e);
      }
      return Optional.of(quote);
    }
    throw new RuntimeException("ERROR: CANNOT FIND VALUE");
  }

  /**
   * CHECK PARAMETER HAS A VALUE
   * @param o object
   * @return boolean
   * @throws IllegalArgumentException PARAMETER IS INVALID
   */
  private Boolean checkParams(Object o){
    if(o instanceof Iterable){
      if(o != null && ((Collection<?>) o).size() > 0)
        return true;
    }
    if(o instanceof String){
      if(o != null && !((String) o).trim().isEmpty())
        return true;
    }
    if(o != null)
      return true;
    throw new IllegalArgumentException("ERROR: INVALID PARAMETER");
  }

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param s must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public boolean existsById(String s) {
    int count = 0;
    if(checkParams(s)){
      String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
      count = jdbcTemplate.queryForObject(query, Integer.class, s);
    }
    return count>0;
  }

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  @Override
  public List<Quote> findAll() {
    String query = "SELECT * FROM " + TABLE_NAME;
    return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Quote.class));
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> strings) {
    throw new UnsupportedOperationException("Not Implemented");
  }

  @Override
  public long count() {
    return findAll().size();
  }

  @Override
  public void deleteById(String s) {
    String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
    jdbcTemplate.update(query, s);
  }

  @Override
  public void delete(Quote entity) {
    throw new UnsupportedOperationException("Not Implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> entities) {
    throw new UnsupportedOperationException("Not Implemented");
  }

  @Override
  public void deleteAll() {
    String query = "DELETE FROM " + TABLE_NAME;
    jdbcTemplate.update(query);
  }
}
