package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  @Before
  public void setUp() throws Exception{
    dao = new TwitterDao(mockHelper);
  }

  @Test
  public void create() throws Exception {
    String hashTag="#hello";
    String text="testing Mockito" + hashTag + " " + System.currentTimeMillis();
    Double lat = 43d;
    Double lon = 79d;
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try{
      dao.create(TweetUtils.buildTweet(text, lon, lat, hashTag));
      fail();
    } catch(Exception e){
      assertTrue(true);
    }

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
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweet_json, Tweet.class);
    //Mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).checkingTweetResponse(any(), anyInt());
    Tweet tweet = spyDao.create(TweetUtils.buildTweet(text, lon, lat, hashTag));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void findById() throws Exception{
    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.findById("1379086580669952");
      fail();
    }catch (Exception e){
      assertTrue(true);
    }
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
    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweet_json, Tweet.class);
    doReturn(expectedTweet).when(spyDao).checkingTweetResponse(any(), anyInt());
    Tweet tweet = spyDao.findById("1379086580669952000");
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteById() throws Exception {
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try{
      dao.deleteById("1379086580669952");
      fail();
    }catch (Exception e){
      assertTrue(true);
    }
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
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweet_json, Tweet.class);
    doReturn(expectedTweet).when(spyDao).checkingTweetResponse(any(), anyInt());
    Tweet tweet = spyDao.findById("1379086580669952000");
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

}