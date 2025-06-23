package com.one.bak.framework.protocol;

import com.one.bak.framework.Invocation;
import com.one.bak.framework.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 * @description: 远程调用的网络服务
 * @author: wanjunjie
 * @date: 2024/10/30
 */
@SPI
public interface Protocol {
    /**
     * Provider开启服务
     */
    void start(URL url);

    /**
     * Consumer进行服务调用
     */
    Object send(URL url, Invocation invocation);

}
