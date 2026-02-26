package com.one.system.results;

/**
 * @ClassName: Result
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/09
 */
public class Result {
    //操作结果编码
    private Integer code;

    //操作数据结构
    private Object data;

    //消息
    private String message;

    //只代Code结果的构造器
    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
