package com.one.object;

import java.time.LocalDate;
import java.util.Objects;

/**
 *  自定义一个 Employee类 , 重写 Object类中的 equals() , hashCode(), toString()方法
 */
public class Employee {
    private String name;
    private double salary;
    private LocalDate hireDay;
    // 空参构造
    public Employee(){
        super();
    }
    //有参构造
    public Employee(String name,double salary,int year,int month,int day){
        super();
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year,month,day);
    }
    // getter和setter方法
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public double getSalary(){
        return this.salary;
    }
    public void setSalary(double salary){
        this.salary = salary;
    }
    public LocalDate getBirthday(){
        return this.hireDay;
    }
    public void setBirthday(int year,int month,int day){
        this.hireDay = LocalDate.of(year,month,day);
    }
    public double raiseSalary(double byPercent){
        double raise = salary*byPercent/100;
        salary +=raise;
        return salary;
    }
    // 重写equals()方法   name,salary和hireDay相同调用 equals()方法返回true
    @Override
    public boolean equals(Object obj){

//      能调用此处的equals()方法的对象必定是Employee的对象,所以不用担心用this.name等
//      重点进行属性的非空校验,而不是担心调用者有没有该属性:
//        public static boolean equals(Object a, Object b) {
//            return (a == b) || (a != null && a.equals(b));
//        }

        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass())return false;
        Employee employee = (Employee)obj;
        return Objects.equals(this.name,employee.name) && (this.salary==employee.salary
                && Objects.equals(this.hireDay,employee.hireDay));
    }

    /**
     *  重写hashCode()方法就是为了让不同的对象有不同的哈希值
     * @return
     */
    @Override
    public int hashCode(){
        //同样能对属性进行非空校验
        return Objects.hash(name,salary,hireDay);
    }

    /**
     *  重写 toString()方法,最好每个类都重写Object类中的toString,这样使用这个类的其他人员
     *  会更方便
     */
    @Override
    public String toString(){
        return this.getClass().getName()+"[ name:"+name+", salary:"+salary+", hireDay:"+hireDay+"]";
    }
}
