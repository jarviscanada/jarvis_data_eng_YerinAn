package ca.jrvs.apps.trading.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  /**
   * Convert a java object to JSON string
   * @param object
   * @param prettyJson
   * @param includeNullValues
   * @return JSON string
   * @throws JsonProcessingException
   */
  public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException{
    ObjectMapper m = new ObjectMapper();
    if( !includeNullValues){
      m.setSerializationInclusion(Include.NON_NULL);
    }
    if(prettyJson){
      m.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return m.writeValueAsString(object);
  }

  public static<T> T toObjectFromJson(String json, Class clazz) throws IOException {
    ObjectMapper m = new ObjectMapper();
    return (T) m.readValue(json, clazz);
  }
}
