package com.one.bak.framework;

import java.util.List;
import java.util.Random;

/**
 * @description: 当有多个provider时, 提供负载均衡
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class LoadBalance {

    public static URL getUrl(List<URL> urlList) {
        // 使用随机进行负载均衡
        Random random = new Random();
        return urlList.get(random.nextInt(urlList.size()));
    }
}
