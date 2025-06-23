package com.one.domain;

/**
 * @ClassName: Order
 * @Description: 订单实体类
 * @Author: one
 * @Date: 2020/12/20
 */
public class Order {
    private String id;
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", msg='" + msg + "}";
    }
}
