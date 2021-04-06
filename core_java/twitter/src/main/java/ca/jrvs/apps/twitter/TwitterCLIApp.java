package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {
  private Controller controller;

  @Autowired
  public TwitterCLIApp(Controller controller){
    this.controller = controller;
  }

  public static void main(String[] args) {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    CrdDao dao = new TwitterDao(httpHelper);
    Service service = new TwitterService(dao);
    Controller controller = new TwitterController(service);
    TwitterCLIApp twitterCLIApp = new TwitterCLIApp(controller);
    twitterCLIApp.run(args);
  }

  public void run(String[] args){
    if(args.length == 0)
      throw new IllegalArgumentException("USAGE: TwitterCLIApp post|show|delete [arguments]");
    String command = args[0];
    switch (command){
      case "post":
        printTweet(this.controller.postTweet(args));
        break;
      case "show":
        printTweet(this.controller.showTweet(args));
        break;
      case "delete":
        this.controller.deleteTweet(args).forEach(this::printTweet);
        break;
      default:
        throw new IllegalArgumentException("USAGE: TwitterCLIApp post|show|delete [arguments]");
    }
  }

  private void printTweet(Tweet tweet){
    try{
      System.out.println(JsonParser.toJson(tweet, true, false));
    }catch (Exception e){
      throw new RuntimeException("ERROR: CANNOT PRINT TWEET IN JSON FORMAT", e);
    }
  }
}
