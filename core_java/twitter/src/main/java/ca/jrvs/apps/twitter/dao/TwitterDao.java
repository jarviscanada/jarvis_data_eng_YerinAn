package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.example.JsonParser;
import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDao implements CrdDao<Tweet, String>{
  //URI constants
  private static final String API_BASE_URI="https://api.twitter.com";
  private static final String POST_PATH="/1.1/statuses/update.json";
  private static final String SHOW_PATH="/1.1/statuses/show.json";
  private static final String DELETE_PATH="/1.1/statuses/destroy";
  //URL symbols
  private static final String QUERY_SYM="?";
  private static final String AMPERSAND="&";
  private static final String EQUAL="=";
  //Response code
  private static final int HTTP_OK=200;
  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper){
    this.httpHelper = httpHelper;
  }

  private String set_coordinate(List<Double> coordinateList){
    return new String(AMPERSAND + "long" + EQUAL + coordinateList.get(0) + AMPERSAND + "lat" +
        EQUAL + coordinateList.get(1));
  }

  private URI setURI(Tweet entity, String api, String string){
    String uri = "";
    switch (api){
      case "POST":
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        Coordinates coordinates = entity.getCoordinates();
        List<Double> coordinateList= coordinates.getCoordinates();
        uri = API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(entity.getText()) + set_coordinate(coordinateList);
        break;
      case "SHOW":
        uri = API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + string;
        break;
      case "DELETE":
        uri = API_BASE_URI + DELETE_PATH + "/" + string + ".json";
        break;
    }
    try {
      return new URI(uri);
    } catch (URISyntaxException e) {
      throw new RuntimeException("ERROR: CANNOT SET UP URI");
    }
  }

  @Override
  public Tweet create(Tweet entity) {
    URI uri = setURI(entity, "POST", null);
    HttpResponse response = null;
    if(uri != null)
      response = httpHelper.httpPost(uri);
    return checkingTweetResponse(response, HTTP_OK);
  }

  @Override
  public Tweet findById(String s) {
    URI uri = setURI(null, "SHOW", s);
    HttpResponse response = null;
    if(uri != null)
      response = httpHelper.httpGet(uri);
    return checkingTweetResponse(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String s) {
    URI uri = setURI(null, "DELETE", s);
    HttpResponse response = null;
    if(uri != null)
      response = httpHelper.httpPost(uri);
    return checkingTweetResponse(response, HTTP_OK);
  }

  public Tweet checkingTweetResponse(HttpResponse response, Integer expectedStatusCode){
    Tweet tweet= null;
    //Check response status
    int actualStatusCode = response.getStatusLine().getStatusCode();
    if(actualStatusCode != expectedStatusCode){
      throw new RuntimeException("ERROR:HTTP STATUS - " + actualStatusCode);
    }
    if(response.getEntity() == null)
      throw new RuntimeException("ERROR:EMPTY RESPONSE BODY");
    String json_str="";
    try{
      json_str = EntityUtils.toString(response.getEntity());
    }catch (Exception e){
      throw new RuntimeException("ERROR:FAILED TO CONVERT ENTITY TO STRING", e);
    }
    try{
      tweet = JsonParser.toObjectFromJson(json_str, Tweet.class);
    }catch (Exception e){
      throw new RuntimeException("ERROR:FAILED TO CONVERT JSON TO OBJECT", e);
    }
    return tweet;
  }
}
