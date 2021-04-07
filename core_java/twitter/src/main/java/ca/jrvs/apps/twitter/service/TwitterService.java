package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service{
  private CrdDao dao;

  @Autowired
  public TwitterService(CrdDao dao){
    this.dao = dao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);
    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    if(!validateId(id))
      throw new IllegalArgumentException("ERROR:ID IS NOT VALID");
    return (Tweet) dao.findById(id);
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> result = new ArrayList<>();
    Arrays.stream(ids).filter(id-> validateId(id)).forEach(id->result.add((Tweet) dao.deleteById(id)));
    return result;
  }

  private void validatePostTweet(Tweet tweet){
    Double longitude = tweet.getCoordinates().getCoordinates().get(0);
    Double latitude = tweet.getCoordinates().getCoordinates().get(1);
    if(tweet.getText().length() > 140){
      throw new IllegalArgumentException("ERROR:TEXT LENGTH EXCEEDED 140 CHARACTERS");
    }
    if(longitude > 180d || longitude < -180d)
      throw new IllegalArgumentException("ERROR:LONGITUDE VALUE IS BETWEEN -180 and 180");
    if(latitude > 90d || latitude < -90d)
      throw new IllegalArgumentException("ERROR:LATITUDE VALUE IS BETWEEN -90 and 90");
  }

  private boolean validateId(String id){
    String regex="^\\d+$";
    return Pattern.compile(regex).matcher(id).matches();
  }
}
