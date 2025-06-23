package com.one.domain;

/**
 * @ClassName: HttpResponse
 * @Description: 统一接口的返回结果
 * @Author: one
 * @Date: 2022/03/24
 */
public class HttpResponseEntity<T> {
    private String status;

    private int code;

    private String errorMsg;

    private T data;

    public HttpResponseEntity() {
    }

    public HttpResponseEntity(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 注意定义静态泛型方法的写法: static<T>
     * @param data 返回数据
     * @param <T> 泛型
     * @return 统一返回结果
     */
    public static<T> HttpResponseEntity<T> ok(T data) {
         return new HttpResponseEntity<T>(200,data);
    }
}
