package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.Hashtag;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TweetUtils {

  public static Tweet buildTweet(String text, Double lon, Double lat, String hashTag) {
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    Entities entities = new Entities();
    tweet.setText(text);
    List<Hashtag> hashtagList = entities.getHashtags()!=null?entities.getHashtags():new ArrayList<>();
    //set coordinates
    coordinates.setCoordinates(Arrays.asList(lon,lat));
    tweet.setCoordinates(coordinates);
    //set hashtag
    Hashtag hashtag = new Hashtag();
    hashtag.setText(hashTag);
    hashtagList.add(hashtag);
    entities.setHashtags(hashtagList);
    tweet.setEntities(entities);
    return tweet;
  }

  public static Tweet buildTweet(String text, Double lon, Double lat) {
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    Entities entities = new Entities();
    tweet.setText(text);
    List<Hashtag> hashtagList = entities.getHashtags()!=null?entities.getHashtags():new ArrayList<>();
    //set coordinates
    coordinates.setCoordinates(Arrays.asList(lon,lat));
    tweet.setCoordinates(coordinates);
    return tweet;
  }

}