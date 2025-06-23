package com.corejava.interfacedemo.interface_1;

/**
 *  Arrays数组工具类中的sort()方法可以对对象数组中存储的元素进行重新排序,前提是该对象类实现了Comparable接口
 *  interface Comparable{
 *      int compareTo(Object obj);  // 必须重写 comparableTo方法
 *  }
 */
public class Employee implements Comparable<Employee> {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    /**
     *  实现Comparable接口,并重写里面的compareTo方法
     * @param
     * @return
     */
    /*@Override
    public int compareTo(Object obj) {
        Employee employee = (Employee)obj;
        if(this.salary >= employee.salary){
            return 1;
        } else {
            return -1;
        }
    }*/

    @Override
    public int compareTo(Employee employee){
        return Double.compare(this.salary,employee.salary);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
