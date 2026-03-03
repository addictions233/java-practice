package com.one.domain;

/**
 * @ClassName: User
 * @Description: pojo类  需求: 当项目中导入jedis坐标时,就在IOC容器中创建User的bean对象,如果不导入就不创建该bean对象
 * @Author: one
 * @Date: 2020/12/16
 */
public class User {

    private String name;

    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

}
