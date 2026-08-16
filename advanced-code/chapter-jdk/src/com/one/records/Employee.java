package com.one.records;

import java.util.Objects;

// 传统POJO类
public final class Employee {
    private final String name;
    private final int id;
    private final Department department;
    
    public Employee(String name, int id, Department department) {
        this.name = name;
        this.id = id;
        this.department = department;
    }
    
    public String getName() { return name; }
    public int getId() { return id; }
    public Department getDepartment() { return department; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && 
               Objects.equals(name, employee.name) &&
               Objects.equals(department, employee.department);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, id, department);
    }
    
    @Override
    public String toString() {
        return "Employee{" +
               "name='" + name +
               ", id=" + id +
               ", department=" + department +
               '}';
    }
}


