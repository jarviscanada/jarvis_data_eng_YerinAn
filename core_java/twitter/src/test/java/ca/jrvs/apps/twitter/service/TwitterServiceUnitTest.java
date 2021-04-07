package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  private Tweet expectedTweet;

  @Before
  public void setUp() throws Exception {
    service = new TwitterService(dao);
    String tweet_json="{\n"
        + "    \"created_at\":\"Mon Apr 05 15:00:37 +0000 2021\",\n"
        + "    \"id\":1379086580669952000,\n"
        + "    \"id_str\":\"1379086580669952000\",\n"
        + "    \"text\":\"test\",\n"
        + "    \"entities\":{\n"
        + "        \"hashtags\":[],"
        + "        \"user_mentions\":[]"
        + "    },\n"
        + "    \"coordinates\":null,\n"
        + "    \"retweet_count\":0,\n"
        + "    \"favorite_count\":0,\n"
        + "    \"favorited\":false,\n"
        + "    \"retweeted\":false\n"
        + "}";
    try{
      this.expectedTweet = JsonParser.toObjectFromJson(tweet_json, Tweet.class);
    }catch (Exception e){
      throw new RuntimeException("ERROR:FAILED TO CONVERT JSON TO OBJECT", e);
    }
  }

  @Test
  public void postTweet() {
    when(dao.create(notNull())).thenReturn(this.expectedTweet);
    //bad
    String bad_text = "Mockito is a mocking framework that allows you to test a class but not the dependencies. "
        + "For instance, `TwitterDao` depends on `HttpHelper`, in the unit test, "
        + "you want to test `TwitterDao` without calling actual `HttpHelper` implementation. "
        + "You can make a mock/fake/dummy object of `HttpHelper` and pass into `TwitterDao`. "
        + "When `TwitterDao` calls the dummy `HttpHelper` object, you have the control on what the dummy object will return.";
    Double bad_lat = 100d;
    Double bad_lon = -190d;
    Double lon = 45d;
    Double lat = 45d;
    String hashtag = "#god";
    String text = "Jesus";
    try{
      //text length is not valid
      service.postTweet(TweetUtils.buildTweet(bad_text, lon, lat, hashtag));
      fail();
    } catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      service.postTweet(TweetUtils.buildTweet(text, bad_lon, lat, hashtag));
      fail();
    } catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      service.postTweet(TweetUtils.buildTweet(text, lon, bad_lat, hashtag));
      fail();
    } catch (IllegalArgumentException e){
      assertTrue(true);
    }
    Tweet tweet = service.postTweet(TweetUtils.buildTweet(text, lon, lat));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void showTweet() {
    when(dao.findById(notNull())).thenReturn(this.expectedTweet);
    try{
      service.showTweet("abcdefg", null);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      service.showTweet("300.27", null);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      service.showTweet("123abc", null);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    Tweet tweet = service.showTweet("1379086580669952000", null);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteTweets() {
    when(dao.deleteById(notNull())).thenReturn(this.expectedTweet);
    try{
      service.deleteTweets(new String[]{"12a","1223"});
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    List<Tweet> tweetList = service.deleteTweets(new String[]{"1379086580669952000", "1379086580669952005"});
    assertNotNull(tweetList);
    assertEquals(2, tweetList.size());
    assertNotNull(tweetList.get(0));
    assertNotNull(tweetList.get(0).getText());
  }
}