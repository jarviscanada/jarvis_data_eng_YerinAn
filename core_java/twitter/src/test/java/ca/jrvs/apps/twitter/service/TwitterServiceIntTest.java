package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  private Service service;
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
    CrdDao dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(dao);
  }

  @Test
  public void T010_postTweet() {
    hashTag="hello";
    text="testing final #" + hashTag + " " + System.currentTimeMillis();
    lat = 43d;
    lon = 79d;
    Tweet postTweet = service.postTweet(TweetUtils.buildTweet(text, lon, lat, hashTag));
    List<Double> coordinateList = postTweet.getCoordinates().getCoordinates();
    id_str = postTweet.getId_str();
    assertEquals(text, postTweet.getText());
    assertNotNull(postTweet.getCoordinates());
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, postTweet.getEntities().getHashtags().get(0).getText());
  }

  @Test
  public void T020_showTweet() {
    Tweet tweet = service.showTweet(id_str, new String[]{});
    List<Double> coordinateList = tweet.getCoordinates().getCoordinates();
    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, tweet.getEntities().getHashtags().get(0).getText());
  }

  @Test
  public void T030_deleteTweets() {
    int num=3;
    String[] id_arr = new String[num];
    String[] text_arr = new String[num];
    //create multiple tweets
    String hash_tag="tag";
    Double lon_ = 45d;
    Double lat_ = 45d;
    for(int i=0; i<num; i++){
      text_arr[i] = "testing multiple insert #" + hash_tag + " " + System.currentTimeMillis();
      Tweet newTweet = service.postTweet(TweetUtils.buildTweet(text_arr[i], lon_, lat_, hash_tag));
      id_arr[i] = newTweet.getId_str();
    }
    List<Tweet> tweetList = service.deleteTweets(id_arr);
    int i=0;
    for(Tweet tweet: tweetList){
      List<Double> coordinateList = tweet.getCoordinates().getCoordinates();
      assertEquals(text_arr[i], tweet.getText());
      assertNotNull(tweet.getCoordinates());
      assertEquals(lon_, coordinateList.get(0));
      assertEquals(lat_, coordinateList.get(1));
      assertEquals(hash_tag, tweet.getEntities().getHashtags().get(0).getText());
      try{
        service.showTweet(tweet.getId_str(), null);
        fail();
      }catch (Exception e){
        assertTrue(true);
      }
      i++;
    }
  }
}