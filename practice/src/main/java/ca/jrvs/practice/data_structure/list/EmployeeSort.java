package ca.jrvs.practice.data_structure.list;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeSort {

  public static void main(String[] args) {
    List<Employee> e_list1 = new ArrayList<>();
    e_list1.add(new Employee(1, "Micheal", 35, 90000));
    e_list1.add(new Employee(2, "Kanye", 27, 70000));
    e_list1.add(new Employee(3, "Dr.Dre", 29, 50000));
    e_list1.add(new Employee(4, "Nujabes", 27, 130000));
    Collections.sort(e_list1, new AgeComparator());
    printString(e_list1, "AGE COMPARATOR");
    Collections.sort(e_list1, new SalaryComparator());
    printString(e_list1, "SALARY COMPARATOR");
    Collections.sort(e_list1, new AgeSalaryComparator());
    printString(e_list1, "AGE & SALARY COMPARATOR");

    List<EmployeeComparable> e_list2 = new ArrayList<>();
    e_list2.add(new EmployeeComparable(1, "Micheal", 35, 90000));
    e_list2.add(new EmployeeComparable(2, "Kanye", 27, 70000));
    e_list2.add(new EmployeeComparable(3, "Dr.Dre", 29, 50000));
    e_list2.add(new EmployeeComparable(4, "Nujabes", 27, 130000));
    Collections.sort(e_list2);
    System.out.println("\n" + "COMPARABLE");
    e_list2.stream().forEach(i->System.out.println(i.getName() + "- Age:" + i.getAge() + " Salary:" + i.getSalary()));
  }

  public static void printString(List<Employee> e, String string){
    System.out.println("\n" +string);
    e.stream().forEach(i->System.out.println(i.getName() + "- Age:" + i.getAge() + " Salary:" + i.getSalary()));
  }

  public static class AgeComparator implements Comparator<Employee>{
    @Override
    public int compare(Employee o1, Employee o2) {
      if (o1.getAge() < o2.getAge()) return -1;
      if (o1.getAge() > o2.getAge()) return 1;
      else return 0;
    }
  }

  public static class SalaryComparator implements Comparator<Employee>{
    @Override
    public int compare(Employee o1, Employee o2) {
      if (o1.getSalary() > o2.getSalary()) return -1;
      if (o1.getSalary() < o2.getSalary()) return 1;
      else return 0;
    }
  }

  public static class AgeSalaryComparator implements Comparator<Employee>{
    @Override
    public int compare(Employee o1, Employee o2) {
      if(o1.getAge() != o2.getAge())
        return o1.getAge() - o2.getAge();
      else{
        if (o1.getSalary() < o2.getSalary()) return -1;
        if (o1.getSalary() > o2.getSalary()) return 1;
        else return 0;
      }
    }
  }

  public static class EmployeeComparable extends Employee implements Comparable<Employee>{

    public EmployeeComparable(){ super();}

    public EmployeeComparable(int id, String name, int age, long salary){
      super(id, name, age, salary);
    }

    @Override
    public int compareTo(Employee o){
      if(this.getAge() != o.getAge())
        return this.getAge() - o.getAge();
      else{
        if (this.getSalary() > this.getSalary()) return -1;
        if (this.getSalary() < this.getSalary()) return 1;
        else return 0;
      }
    }
  }
}
