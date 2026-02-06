package com.one.aopinvalid;

/**
 * 使用接口来进行自注入, 解决对象内部调用AOP失效的问题
 * @param <T>
 */
public interface InjectSelf<T> {

    void setSelf(T self);
}
