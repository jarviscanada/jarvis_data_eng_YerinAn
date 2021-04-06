package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
  @Mock
  Service service;
  @InjectMocks
  TwitterController controller;

  Tweet expectedTweet;

  @Before
  public void setUp() throws Exception {
    controller = new TwitterController(service);
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
    when(service.postTweet(notNull())).thenReturn(this.expectedTweet);
    try{
      controller.postTweet(new String[]{"post", "text"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.postTweet(new String[]{"post", "text", "50:-50", "hello"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.postTweet(new String[]{"show", "text", "50:-50"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.postTweet(new String[]{"post", "text", "50:"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.postTweet(new String[]{"post", "text", "50:-50k"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    Tweet tweet = controller.postTweet(new String[]{"post", "text", "50:-50"});
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void showTweet() {
    when(service.showTweet(notNull(), any())).thenReturn(expectedTweet);
    try{
      controller.showTweet(new String[]{"show"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.showTweet(new String[]{"show", "1379086580669952000", "hello"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.showTweet(new String[]{"post", "1379086580669952000"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    Tweet tweet = controller.showTweet(new String[]{"show", "1379086580669952000"});
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteTweet() {
    List<Tweet> tweetList = new ArrayList<>();
    for(int i = 0; i <3; i++)
      tweetList.add(expectedTweet);
    when(service.deleteTweets(notNull())).thenReturn(tweetList);
    try{
      controller.deleteTweet(new String[]{"delete"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.deleteTweet(new String[]{"delete", "1111, 2222", "hello"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    try{
      controller.deleteTweet(new String[]{"show", "1111, 2222"});
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
    controller.deleteTweet(new String[]{"delete", "1379086580669952000, 1379086580669952007, 1379086580669952005"}).forEach(i->{
      assertNotNull(i);
      assertNotNull(i.getText());
    });
  }
}