package com.one.domain;

import java.util.List;

/**
 * @ClassName: User
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/06
 */
public class User {
    private String id;
    private String name;
    private int age;

    private Address address;

    private List<String> hobby;

    private List<Address> addresses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", hobby=" + hobby +
                ", addresses=" + addresses +
                '}';
    }
}
