package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep{
  private String regex;
  private String rootPath;
  private String outFile;

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  @Override
  public void process() throws IOException {
    List<File> fileList = listFiles(this.rootPath);
    List<String> matchedLines = new ArrayList<>();
    for(File f : fileList) {
      List<String> readLines = readLines(f);
      for(String s : readLines){
        if(containsPattern(s)){
          matchedLines.add(s);
        }
      }
    }
    writeToFile(matchedLines);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter bufferedWriter = null;
    OutputStream outputStream = null;
    try{
      outputStream = new FileOutputStream(new File(this.outFile));
      bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
      for(String s : lines){
        bufferedWriter.write(s + "\n");
      }
      bufferedWriter.close();
      outputStream.close();
    }catch (Exception e){
      throw new IOException("ERROR: CANNOT WRITE", e);
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File files = new File(rootDir);
    List<File> result = new ArrayList<>();
    for(File f : files.listFiles()){
      if(f.isFile()){
        result.add(f);
      }else if (f.isDirectory()){
        result.addAll(listFiles(f.getAbsolutePath()));
      }
    }
    return result;
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
    List<String> result = new ArrayList<>();
    BufferedReader bufferedReader = null;
    try{
      bufferedReader = new BufferedReader(new FileReader(inputFile));
      result = bufferedReader.lines().filter(Objects::nonNull).collect(Collectors.toList());
    }catch (FileNotFoundException e){
      throw new IllegalArgumentException("ERROR: FILE NOT FOUND", e);
    }
    finally {
      if(bufferedReader != null)
        bufferedReader.close();
    }
    return result;
  }

  /**
   * Check if a line contains regex pattern
   * @param line input string
   * @return true if string is matched
   */
  @Override
  public boolean containsPattern(String line) {
    return Pattern.compile(getRegex()).matcher(line).matches();
  }

  @Override
  public String getRootPath() { return rootPath; }

  @Override
  public void setRootPath(String rootPath) { this.rootPath = rootPath; }

  @Override
  public String getRegex() { return regex; }

  @Override
  public void setRegex(String regex) { this.regex = regex; }

  @Override
  public String getOutFile() { return outFile; }

  @Override
  public void setOutFile(String outFile) { this.outFile = outFile; }

  public static void main(String[] args){
    if(args.length != 3){
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    //use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try{
      javaGrepImp.process();
    }catch (Exception ex){
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }
}
