package com.one.nacos.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: phone
 * @Description: 实体类
 * @Author: one
 * @Date: 2021/03/04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    private Integer id;
    private String name;
    private Double price;
    private String desc;

}
