package com.one.gateway.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Product
 * @Description: TODO
 * @Author: one
 * @Date: 2021/01/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private Double price;
    private String desc;
}
