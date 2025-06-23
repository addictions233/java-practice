package com.one.consumer.pojo;

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
    private String title;
    private String name;
    private Double price;
    private Integer count;
}
