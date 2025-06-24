package com.one.domain.dp;

import lombok.Data;

/**
 * 在DDD中, DP(Domain Primitive)可以说是一切模型,方法, 架构的基础.
 * 它在特点领域, 拥有精准定义, 可以自我验证, 拥有行为的对象. 可以认为是领域的最小组成部分.
 * DP: 抽象并封装自检和一些隐形属性的计算逻辑, 且这些属性是无状态的.
 */
@Data
public class PhoneNumber {

    private String number;

    private String areaCode;

    private String operatorCode;

    private final String pattern = "^0?[1-9]{2-3}-?\\d{8}%";

    /**
     * 仅包含有参构造函数
     * 实体类有自己的业务处理逻辑,属于充血模型了
     * @param number 手机号
     */
    public PhoneNumber(String number) {
        // 自校验
        if (number == null) {
            throw new IllegalArgumentException("number不能为空");
        }
        if (!isValid(number)) {
            throw new IllegalArgumentException("number格式错误");
        }
        this.number = number;
    }

    private boolean isValid(String number) {
        return number.matches(pattern);
    }

    public String getOperatorCode(PhoneNumber phone) {
        // TODO
        return null;
    }
}
