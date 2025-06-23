package com.one.video.controller;

import com.one.domain.CloudVideo;
import com.one.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: VideoController
 * @Description: controller层
 * @Author: one
 * @Date: 2022/04/10
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 查询视频表中的数据
     * @param videoId 查询id
     * @param request 请求对象
     * @return com.one.domain.CloudVideo
     */
//    @ResponseBody
    @GetMapping("queryVideo/{videoId}")
    public CloudVideo findVideoById(@PathVariable("videoId") String videoId, HttpServletRequest request) {
        String serverInfo = request.getServerName() + ":" + request.getServerPort();
        // 记录调用的服务地址
        CloudVideo video = videoService.findVideoById(videoId);
        video.setServeInfo(serverInfo);
        return video;
    }

    /**
     * 保存视频表中的数据
     * @param video 实体对象
     */
    @PostMapping("/saveVideo")
    public void saveVideo(@RequestBody CloudVideo video) {
        videoService.saveVideo(video);
    }

}
