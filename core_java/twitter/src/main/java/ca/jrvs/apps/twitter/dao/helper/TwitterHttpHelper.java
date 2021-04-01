package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@SuppressWarnings("Duplicates")
@Component
public class TwitterHttpHelper implements HttpHelper{

  /**
   * Dependencies are specified as private member variables
   */
  private OAuthConsumer consumer;
  private HttpClient httpClient;

  /**
   * Constructor
   * Setup dependencies using secrets
   * @param consumerKey
   * @param consumerSecret
   * @param accessToken
   * @param tokenSecret
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    /**
     * Default = single connection. Discuss source code if time permit
     */
    httpClient = new DefaultHttpClient();
  }

  private HttpResponse executeHttpRequest(HttpMethod method, URI uri) throws Exception{
    if(method == HttpMethod.GET){
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    }else if(method == HttpMethod.POST){
      HttpPost request = new HttpPost(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    }
    return null;
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.POST, uri);
    } catch (Exception e) {
      throw new RuntimeException("ERROR: POST EXECUTE", e);
    }
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    try{
      return executeHttpRequest(HttpMethod.GET, uri);
    } catch (Exception e) {
      throw new RuntimeException("ERROR: GET EXECUTE", e);
    }
  }

  public static void main(String[] args) throws Exception {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    HttpResponse response = httpHelper
        .httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=happy_holidays"));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
}
