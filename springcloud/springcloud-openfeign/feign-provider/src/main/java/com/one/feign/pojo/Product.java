package com.one.feign.pojo;

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
@NoArgsConstructor
public class Product {
    private String title;
    private String name;
    private Double price;
    private Integer count;

    public Product(String title, String name, Double price, Integer count) {
        this.title = title;
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
