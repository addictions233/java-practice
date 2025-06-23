package com.one.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: Student
 * @Description: 学生类
 * @Author: one
 * @Date: 2022/04/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends User {
    private String schoolName;

    private String studentCardId;
}
