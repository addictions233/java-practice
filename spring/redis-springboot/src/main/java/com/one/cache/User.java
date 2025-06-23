package com.one.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/10/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

    private Date birthday;
}
