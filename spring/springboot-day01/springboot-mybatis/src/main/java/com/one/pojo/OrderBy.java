package com.one.pojo;

import lombok.Data;

/**
 * @ClassName: OrderBy
 * @Description: 进行排序的DTO
 * @Author: one
 * @Date: 2022/01/20
 */
@Data
public class OrderBy {
    /**
     * 排序的字段名称
     */
    private String field;

    /**
     * 是否是降序排序, true表示是降序
     */
    private boolean desc;
}
