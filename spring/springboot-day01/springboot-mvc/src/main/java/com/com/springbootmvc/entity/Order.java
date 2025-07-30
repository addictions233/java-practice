package com.com.springbootmvc.entity;

import com.com.springbootmvc.serializer.BigDecimalSerializer;
import com.com.springbootmvc.serializer.EnumJsonDeserilizer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author one
 * @description TODO
 * @date 2023-3-14
 */
@Data
@Accessors(chain = true)
public class Order {
    private Long id;
    /**
     * 数量
     */
    private Integer number;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 总价
     */
    @JsonSerialize(using = BigDecimalSerializer.class)
    @JsonDeserialize(using = EnumJsonDeserilizer.class)
    private BigDecimal totalAmount;

    /**
     * 单价
     */
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;
}
