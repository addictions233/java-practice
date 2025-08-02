package com.one.eastrules;

import lombok.Data;

@Data
public class Person {

    public Person(String name,Integer age){
        this.name = name;
        this.age = age;
    }

    private boolean adult;

    private String name;

    private Integer age;
}
