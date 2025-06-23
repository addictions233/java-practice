package com.one.order.feign.fallback;

import com.one.domain.CloudVideo;
import com.one.order.feign.VideoServiceFeign;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName: FallBackVideoService
 * @Description: 当feign接口远程调用失败后,使用实现类中的结果作为返回结果
 * @Author: one
 * @Date: 2022/04/12
 */
@Service
public class FallBackVideoService implements VideoServiceFeign {
    /**
     * 查询视频表中的数据
     *
     * @param videoId 查询id
     * @return com.one.domain.CloudVideo
     */
    @Override
    public CloudVideo getVideoById(String videoId) {
        CloudVideo video = new CloudVideo();
        video.setCreateTime(new Date());
        video.setTitle("这是fallback中的视频");
        return video;
    }
}
