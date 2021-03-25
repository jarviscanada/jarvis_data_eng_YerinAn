package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JavaGrepLambdaImp extends JavaGrepImp{

  public static void main(String[] args) {
    if(args.length != 3){
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try{
      javaGrepLambdaImp.process();
    }catch (Exception ex){
      javaGrepLambdaImp.logger.error(ex.getMessage(), ex);
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> result = new ArrayList<>();
    try {
      Files.walk(Paths.get(rootDir))
          .filter(Files::isRegularFile)
          .map(f->f.toFile())
          .forEach(f->result.add(f));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
    List<String> result = new ArrayList<>();
    Files.lines(Paths.get(inputFile.getAbsolutePath()))
        .filter(Objects::nonNull)
        .forEach(s -> result.add(s));
    return result;
  }
}
