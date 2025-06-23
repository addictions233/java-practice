package com.one.order.feign;

import com.one.domain.CloudVideo;
import com.one.order.config.FeignLogConfig;
import com.one.order.feign.fallback.FallBackVideoService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @InterfaceName: VideoServiceFeign
 * @Description: 使用openFeign远程调用video-service的接口服务
 * @Author: one
 * @Date: 2022/04/10
 */
@FeignClient(value = "video-service", configuration = FeignLogConfig.class, fallback = FallBackVideoService.class)
public interface VideoServiceFeign {
    /**
     * 查询视频表中的数据
     * @param videoId 查询id
     * @return com.one.domain.CloudVideo
     */
    @GetMapping("/video/queryVideo/{videoId}")
    CloudVideo getVideoById(@PathVariable("videoId") String videoId);
}
