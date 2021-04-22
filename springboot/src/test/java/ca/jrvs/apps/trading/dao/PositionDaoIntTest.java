package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
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
public class PositionDaoIntTest {

  @Autowired
  private PositionDao positionDao;
  @Autowired
  private SecurityOrderDao securityOrderDao;
  @Autowired
  private QuoteDao quoteDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;

  private Position position;
  private SecurityOrder securityOrder;


  @Before
  public void setUp() throws Exception {
    position = new Position();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void findById(){
    Optional<Position> result = positionDao.findById(position.getAccountId());
    assertTrue(result.isPresent());
  }

  @Test
  public void findAllById(){
    List<Position> results = positionDao.findAllById(Arrays.asList(position.getAccountId()));
    assertEquals(position.getAccountId(), results.get(0).getAccountId());
  }

  @Test
  public void findAll(){
    List<Position>  results = positionDao.findAll();
    assertEquals(1, results.size());
  }

  @Test
  public void count(){
    assertEquals(1, positionDao.count());
  }

  @Test
  public void existsById(){
    assertTrue(positionDao.existsById(position.getAccountId()));
  }

}