package com.one.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 库存系统: 订单支付成功了, 对应的货物库存数量要减
 * @author: wanjunjie
 * @date: 2024/04/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageSystem {
    /**
     * 货物ID
     */
    private String productId;

    /**
     * 货物名称
     */
    private String productName;

    /**
     * 库存数量
     */
    private Integer storeCount;
}
