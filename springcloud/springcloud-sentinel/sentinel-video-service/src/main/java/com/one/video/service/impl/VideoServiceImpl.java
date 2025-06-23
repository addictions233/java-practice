package com.one.video.service.impl;

import com.one.domain.CloudVideo;
import com.one.video.dao.VideoMapper;
import com.one.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: VideoServiceImpl
 * @Description: service层实现类
 * @Author: one
 * @Date: 2022/04/10
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    /**
     * 通过id查询video对象
     *
     * @param videoId 主键id
     * @return CloudVideo
     */
    @Override
    public CloudVideo findVideoById(String videoId) {
        return videoMapper.findVideoById(videoId);
    }

    /**
     * 保存video对象
     *
     * @param video 实体对象
     */
    @Override
    public void saveVideo(CloudVideo video) {
        videoMapper.saveVideo(video);
    }
}
