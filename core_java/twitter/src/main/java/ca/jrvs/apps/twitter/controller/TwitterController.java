package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.utils.TweetUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{

  private Service service;
  private static final String COORD_SEP = ":";
  private static final String COMMA =",";

  @Autowired
  public TwitterController(Service service){
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {
    if(args.length != 3)
      throw new IllegalArgumentException("ERROR: INVALID NUMBER OF ARGUMENTS");
    String text = args[1];
    String[] coordinate_arr = args[2].split(COORD_SEP);
    if(coordinate_arr.length !=2 || StringUtils.isEmpty(text))
      throw new IllegalArgumentException("ERROR: INVALID LOCATION FORMAT");
    if(!args[0].equalsIgnoreCase("post"))
      throw new IllegalArgumentException("ERROR: INVALID ACTION");
    Double lat = null;
    Double lon = null;
    try{
      lat = Double.parseDouble(coordinate_arr[0]);
      lon = Double.parseDouble(coordinate_arr[1]);
    }catch (Exception e){
      throw new IllegalArgumentException("ERROR: CANNOT GET LOCATION FORMAT");
    }
    Tweet tweet = TweetUtils.buildTweet(text, lon, lat);
    return service.postTweet(tweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if(args.length != 2)
      throw new IllegalArgumentException("ERROR: INVALID NUMBER OF ARGUMENTS");
    if(!args[0].equalsIgnoreCase("show"))
      throw new IllegalArgumentException("ERROR: INVALID ACTION");
    return service.showTweet(args[1], null);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if(!args[0].equalsIgnoreCase("delete"))
      throw new IllegalArgumentException("ERROR: INVALID ACTION");
    String[] id_arr = args[1].split(COMMA);
    return service.deleteTweets(id_arr);
  }
}
