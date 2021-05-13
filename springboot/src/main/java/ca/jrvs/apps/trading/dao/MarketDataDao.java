package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.utils.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * MarketDataDao is responsible for getting Quotes from IEX
 */
@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH="/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;
  private static final int HTTP_OK=200;
  private final int HTTP_NOT_FOUND=404;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig){
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }

  /**
   * Get an IexQuote (helper method which class findAllById)
   * @param ticker
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote = null;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));
    if(quotes.size()==0)
      return Optional.empty();
    else if(quotes.size() == 1)
      iexQuote = Optional.of(quotes.get(0));
    else
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    return iexQuote;
  }

  /**
   * Get quotes from IEX
   * @param tickers is a list of tickers
   * @return a list of IexQuote object
   * @throws IllegalArgumentException if any ticker is invalid or tickers is empty
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    List<IexQuote> results = new ArrayList<>();
    Optional<String> response = executeHttpGet(String.format(IEX_BATCH_URL,
        String.join(",", tickers)));
    if(response.isPresent()){
      JSONObject jsonObject = new JSONObject(response.get());
      for(String ticker: tickers){
        if(!jsonObject.has(ticker))
          throw new IllegalArgumentException("ERROR: INVALID TICKER " + ticker);
        try {
          IexQuote quote = JsonParser.toObjectFromJson(jsonObject.getJSONObject(ticker).get("quote").toString(), IexQuote.class);
          results.add(quote);
        } catch (IOException e) {
          throw new DataRetrievalFailureException("ERROR: FAILED TO CONVERT JSON TO OBJECT", e);
        }
      }
      return results;
    }
    throw new DataRetrievalFailureException("ERROR: HTTP RESPONSE FAILED");
  }

  /**
   * Execute a get and return http entity/body as a string
   * Tip: use EntityUtils.toString to process HTTP entity
   * @param url resource URL
   * @return http response body or Optional.empty for 404 response
   * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
   */
  private Optional<String> executeHttpGet(String url){
    HttpResponse response = checkGetRequest(url);
    Optional<String> result = Optional.empty();
    int actualStatusCode = response.getStatusLine().getStatusCode();
    if(actualStatusCode == HTTP_NOT_FOUND)
      return result;
    if(actualStatusCode == HTTP_OK){
      try{
        result = Optional.of(EntityUtils.toString(response.getEntity()));
        return result;
      }catch (Exception e){
        throw new DataRetrievalFailureException("ERROR:FAILED TO CONVERT ENTITY TO STRING", e);
      }
    }
    throw new DataRetrievalFailureException("ERROR:HTTP STATUS - " + actualStatusCode);
  }

  private HttpResponse checkGetRequest(String url){
    HttpResponse response = null;
    if(url!=null && !url.trim().isEmpty()){
      CloseableHttpClient httpClient = getHttpClient();
      try{
        response = httpClient.execute(new HttpGet(url));
      }catch (IOException e){
        throw new DataRetrievalFailureException("FAILED TO EXECUTE REQUEST", e);
      }
      return response;
    }
    throw new IllegalArgumentException("ERROR: INVALID URL");
  }

  /**
   * Borrow a HTTP client from the httpClientConnectionManager
   * @return a httpClient
   */
  private CloseableHttpClient getHttpClient(){
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        //prevent connectionManager shutdown when calling httpClient.close()
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public <S extends IexQuote> S save(S entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> entities) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> entities) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }
}