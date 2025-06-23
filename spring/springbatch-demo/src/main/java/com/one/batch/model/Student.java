package com.one.batch.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import javax.persistence.*;
 
/**
 * student 表格的实体类
 * 
 * @author 01
 * @date 2019-02-24
 **/
@Data
@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    private String name;
 
    private Integer age;
 
    private String sex;
 
    private String address;
 
    private Integer cid;
}