package com.one.pojo;

import lombok.Data;

/**
 * @ClassName: ConditionQuery
 * @Description: 进行多条件查的DTO, 枚举和搜索查询
 * @Author: one
 * @Date: 2022/01/20
 */
@Data
public class ConditionQuery {
    /**
     * 条件查询的类型: search: 表示输入值过滤,  enumerate: 表示枚举值过滤
     */
    private String conditionType;

    /**
     * 条件查询的字段名称
     */
    private String conditionKey;

    /**
     * 条件查询的值: 如果是search类型, 那么就是字符串类型,  如果是enumerate类型,那么就是数组类型
     */
    private Object conditionValue;
}
