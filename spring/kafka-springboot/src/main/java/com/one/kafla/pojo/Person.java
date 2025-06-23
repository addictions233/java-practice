package com.one.kafla.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Person
 * @Description: TODO
 * @Author: one
 * @Date: 2021/01/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    String name;
    Integer age;
    String gender;
}
