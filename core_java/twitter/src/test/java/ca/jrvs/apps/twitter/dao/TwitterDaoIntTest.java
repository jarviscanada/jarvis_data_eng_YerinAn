package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao twitterDao;
  private static String id_str;
  private static String text="";
  private static String hashTag="";
  private static Double lat = null;
  private static Double lon = null;

  @Before
  public void setUp() throws Exception {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() {
    hashTag="hello";
    text="testing final #" + hashTag + " " + System.currentTimeMillis();
    lat = 43d;
    lon = 79d;
    Tweet postTweet = twitterDao.create(TweetUtils.buildTweet(text, lon, lat, hashTag));
    List<Double> coordinateList = postTweet.getCoordinates().getCoordinates();
    id_str = postTweet.getId_str();
    assertEquals(text, postTweet.getText());
    assertNotNull(postTweet.getCoordinates());
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, postTweet.getEntities().getHashtags().get(0).getText());
  }

  @Test
  public void findById() {
    Tweet getTweet = twitterDao.findById(id_str);
    List<Double> coordinateList = getTweet.getCoordinates().getCoordinates();
    assertEquals(text, getTweet.getText());
    assertNotNull(getTweet.getCoordinates());
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, getTweet.getEntities().getHashtags().get(0).getText());
  }

  @Test
  public void deleteById() {
    Tweet tweet = twitterDao.deleteById(id_str);
    List<Double> coordinateList = tweet.getCoordinates().getCoordinates();
    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, tweet.getEntities().getHashtags().get(0).getText());
    try{
      twitterDao.findById(id_str);
      fail();
    }catch (Exception e){
      assertTrue(true);
    }
  }
}