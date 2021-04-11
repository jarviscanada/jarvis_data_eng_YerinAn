package ca.jrvs.practice.data_structure.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
  private int id;
  private String name;
  private int age;
  private long salary;

  public static void main(String[] args) {
    Map<Employee, List<String>> empStrMap = new HashMap<>();
    Employee any = new Employee(1, "Any", 25, 45000);
    List<String> anyPreviousCompanies = Arrays.asList("ID", "RBC", "CIBC");
    empStrMap.put(any, anyPreviousCompanies);
    Employee bob = new Employee(1, "Bob", 30, 75000);
    List<String> anyPreviousCompanies_bob = Arrays.asList("APPLE", "MicroSoft", "FaceBook");
    empStrMap.put(bob, anyPreviousCompanies_bob);
    System.out.println("Bob hashcode" + bob.hashCode());
    System.out.println("Bob value" + empStrMap.get(bob).toString());
  }

  public Employee() {
  }

  public Employee(int id, String name, int age, long salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}