package com.one.order.controller;

import com.one.domain.CloudVideoOrder;
import com.one.domain.CloudVideo;
import com.one.order.feign.VideoServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: OrderController
 * @Description: controller层, 测试sentinel的限流, 熔断降级, 隔离
 *         sentinel的限流策略: 1,设置QPS阀值,  2,设置并发线程数阀值
 *         sentinel的熔断降级策略: 1,多次请求的异常比例
 * @Author: one
 * @Date: 2022/04/10
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private VideoServiceFeign videoServiceFeign;

    /**
     * sentinel中的限流配置有两种: 作用到具体的接口层级,也可以对不同的调用方设置单独的限流规则
     *   1,基于QPS, 该接口的每秒访问次数超过一次数额,该接口就会限制访问
     *   2,基于线程数, 多个请求占用的线程数超过设置线程数额, 该接口就限制访问
     * @param videoId
     * @return
     */
    @GetMapping("/save")
    public CloudVideoOrder videoOrder(@RequestParam("videoId") String videoId) {
        // 使用feign调用order服务
        CloudVideo video = videoServiceFeign.getVideoById(videoId);
        CloudVideoOrder videoOrder = new CloudVideoOrder();
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setServerInfo(video.getServeInfo());
        return videoOrder;
    }

    /**
     * 测试sentinel基于线程数的限流设置: 同一时间访问接口的线程数超过一定数额,就让该接口无法访问
     * @return Map
     */
    @GetMapping("/queryVideoTitle")
    public Map<String, String> getVideoTitle() {
        try {
            // 让线程休眠3秒,测试同一接口的并发访问线程数超过额定数组, sentinel对接口进行限流
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, String> titleMap = new HashMap<String, String>() {
            {
                put("title1", "天龙八部");
                put("title2", "大话西游");
            }
        };
        return titleMap;
    }
}
