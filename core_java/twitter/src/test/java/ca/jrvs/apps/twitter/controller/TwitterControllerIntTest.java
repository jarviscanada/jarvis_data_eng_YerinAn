package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

  private Controller controller;
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
    Service service = new TwitterService(dao);
    this.controller = new TwitterController(service);
  }

  @Test
  public void T010_postTweet() {
    hashTag="hash";
    text="controller Testing00 #" + hashTag + " " + System.currentTimeMillis();
    lat = 43d;
    lon = 79d;
    Tweet tweet = controller.postTweet(new String[]{"post", text, lat + ":" + lon});
    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    List<Double> coordinateList = tweet.getCoordinates().getCoordinates();
    id_str = tweet.getId_str();
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, tweet.getEntities().getHashtags().get(0).getText());
  }

  @Test
  public void T020_showTweet() {
    Tweet tweet =  controller.showTweet(new String[]{"show", id_str});
    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    List<Double> coordinateList = tweet.getCoordinates().getCoordinates();
    assertEquals(lon, coordinateList.get(0));
    assertEquals(lat, coordinateList.get(1));
    assertEquals(hashTag, tweet.getEntities().getHashtags().get(0).getText());
  }

  @Test
  public void T030_deleteTweet() {
    int num=3;
    String[] id_arr = new String[num];
    String[] text_arr = new String[num];
    //create multiple tweets
    String hash_tag="tag";
    Double lon_ = 45d;
    Double lat_ = 45d;
    for(int i=0; i<num; i++){
      text_arr[i] = "testing multiple insert" + String.valueOf(i) +" #" + hash_tag + " " + System.currentTimeMillis();
      Tweet newTweet = controller.postTweet(new String[]{"post", text_arr[i], lat_ + ":" + lon_});
      id_arr[i] = newTweet.getId_str();
    }
    List<Tweet> tweetList = controller.deleteTweet(new String[]{"delete", id_arr.toString()});
    int i=0;
    for(Tweet tweet: tweetList){
      List<Double> coordinateList = tweet.getCoordinates().getCoordinates();
      assertEquals(text_arr[i], tweet.getText());
      assertNotNull(tweet.getCoordinates());
      assertEquals(lon_, coordinateList.get(0));
      assertEquals(lat_, coordinateList.get(1));
      assertEquals(hash_tag, tweet.getEntities().getHashtags().get(0).getText());
      try{
        controller.showTweet(new String[]{"show", tweet.getId_str()});
        fail();
      }catch (Exception e){
        assertTrue(true);
      }
      i++;
    }
  }
}