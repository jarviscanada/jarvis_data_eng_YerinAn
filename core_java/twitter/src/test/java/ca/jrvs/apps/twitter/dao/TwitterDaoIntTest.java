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
    String hashTag="hello";
    String text="testing create tweet2 #" + hashTag + " " + System.currentTimeMillis();
    Double lat = 43d;
    Double lon = 79d;
    Tweet postTweet = twitterDao.create(TweetUtils.buildTweet(text, lon, lat));
    List<Double> coordinateList = postTweet.getCoordinates().getCoordinates();
    assertEquals(text, postTweet.getText());
    assertNotNull(postTweet.getCoordinates());
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, postTweet.getEntities().getHashtags().get(0).getText());
  }

//  @Test
//  public void findById() {
//  }
//
//  @Test
//  public void deleteById() {
//  }
}