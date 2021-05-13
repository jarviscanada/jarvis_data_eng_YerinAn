package ca.jrvs.apps.trading.utils;

public enum Status {NULL("NULL"),
  FILLED("FILLED"),
  CANCELLED("CANCELLED"),
  PENDING("PENDING");

  private String value;

  private Status(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String toString() {
    if (this == NULL) {
      return "";
    }

    return value;
  }
}
