package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {
  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);
  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "account_id";

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public PositionDao(DataSource dataSource){
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public Optional<Position> findById(Integer id) {
    Optional<Position> entity = Optional.empty();
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " =?";
    try{
      entity = Optional.ofNullable((Position) jdbcTemplate
          .queryForObject(selectSql,
              BeanPropertyRowMapper.newInstance(Position.class), id));
    } catch (IncorrectResultSizeDataAccessException e){
      logger.debug("Can't find trader id:" + id, e);
    }
    return entity;
  }

  public List<Position> findAll() {
    String query = "SELECT * FROM " + TABLE_NAME;
    return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Position.class));
  }

  public List<Position> findAllById(Iterable<Integer> ids) {
    List<Position> entities = new ArrayList<>();
    ids.forEach(id -> entities.add(findById(id).get()));
    return entities;
  }

  public long count() {
    return findAll().size();
  }

  public boolean existsById(Integer id) {
    return findById(id).isPresent();
  }
}