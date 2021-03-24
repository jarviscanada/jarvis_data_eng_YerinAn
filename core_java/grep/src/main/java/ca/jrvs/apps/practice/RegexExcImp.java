package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc{

  public static void main(String[] args) {
    RegexExcImp a = new RegexExcImp();
    System.out.println(a.matchJpeg("aaa.jpEg"));
    System.out.println(a.matchIp("999.999.999.9999")); //999.999.999.999  3.3.0.0.0.0
    System.out.println(a.isEmptyLine(" aaa"));
  }

  @Override
  public boolean matchJpeg(String filename) {
    String regex = "^\\S+\\.jpe?g$";
    return filename.matches(regex);
  }

  @Override
  public boolean matchIp(String ip) {
    String regex = "(\\d{1,3}\\.){3}(\\d){1,3}";
    return ip.matches(regex);
  }

  @Override
  public boolean isEmptyLine(String line) {
    String regex = "\\s{0,1}";
    return line.matches(regex);
  }
}
