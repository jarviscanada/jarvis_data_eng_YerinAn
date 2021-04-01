package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entities {
  @JsonProperty("hashtags")
  List<Hashtag> hashtags;
  @JsonProperty("user_mentions")
  List<UserMention> userMentions;

  public List<Hashtag> getHashtags() {
    return hashtags;
  }

  public void setHashtags(List<Hashtag> hashtags) {
    this.hashtags = hashtags;
  }

  public List<UserMention> getUserMentions() {
    return userMentions;
  }

  public void setUserMentions(List<UserMention> userMentions) {
    this.userMentions = userMentions;
  }
}
