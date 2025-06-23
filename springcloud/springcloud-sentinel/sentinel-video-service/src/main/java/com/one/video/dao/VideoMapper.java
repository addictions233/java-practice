package com.one.video.dao;

import com.one.domain.CloudVideo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: VideoMapper
 * @Description: Dao层接口
 * @Author: one
 * @Date: 2022/04/10
 */
@Repository
public interface VideoMapper {
    /**
     * 通过id查询video对象
     * @param videoId 主键id
     * @return video对象
     */
    @Select("select id,title,summary,cover_img,price,create_time,point from cloud_video where id = #{videoId}")
    CloudVideo findVideoById(String videoId);

    /**
     * 保存video对象
     * @param video 实体对象
     */
    @Insert("insert into cloud_video (title,summary,cover_img,price,point) values (#{title},#{summary},#{coverImg},#{price},#{point})")
    void saveVideo(CloudVideo video);
}
