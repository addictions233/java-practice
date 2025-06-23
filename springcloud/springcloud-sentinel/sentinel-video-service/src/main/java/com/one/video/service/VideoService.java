package com.one.video.service;

import com.one.domain.CloudVideo;

/**
 * @InterfaceName: VideoService
 * @Description: TODO
 * @Author: one
 * @Date: 2022/04/10
 */
public interface VideoService {
    /**
     * 通过id查询video对象
     * @param videoId 主键id
     * @return CloudVideo
     */
    CloudVideo findVideoById(String videoId);

    /**
     * 保存video对象
     * @param video 实体对象
     */
    void saveVideo(CloudVideo video);
}
