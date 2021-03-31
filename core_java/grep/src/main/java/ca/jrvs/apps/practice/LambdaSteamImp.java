package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaSteamImp implements LambdaStreamExc{

  public static void main(String[] args) {
    String[] messages = {"hello", "world", "Java"};
    LambdaSteamImp lam = new LambdaSteamImp();
    lam.printMessage(messages, lam.getLambdaPrinter("[", "]"));
    lam.printOdd(lam.createIntStream(1,10), lam.getLambdaPrinter("oddNum: ", "."));
    lam.filter(lam.toUpperCase(messages), "\\w").forEach(System.out::println);
    lam.filter(lam.createStrStream(messages), "\\w").forEach(System.out::println);
    lam.toList(lam.createIntStream(1,3)).forEach(System.out::println);
  }

  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    Stream<String> stringStream = createStrStream(strings);
    return stringStream.map(String::toUpperCase);
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    String regex = ".*" + pattern + ".*";
    return stringStream.filter(str -> Pattern.compile(regex).matcher(str).matches());
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.mapToDouble(num -> Math.sqrt(num));
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(num -> num % 2 != 0);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    Consumer<String> consumer = (String message) -> System.out.println(prefix + message + suffix);
    return consumer;
  }

  @Override
  public void printMessage(String[] messages, Consumer<String> printer) {
    for(String m : messages)
      printer.accept(m);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).forEach(n -> printer.accept(String.valueOf(n)));
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(list -> list.stream().map(n -> n * n));
  }
}
