package com.one.domain;



import lombok.Data;

import java.util.Date;

/**
 * @Description 与cloud_video_order表相映射的实体类
 * @Author one
 * @Version 1.0
 **/
@Data
public class CloudVideoOrder {
    private Integer id;
    private String outTradeNo;
    private Integer state;
    private Date createTime;
    private  Integer totalFee;
    private Integer videoId;
    private String videoTitle;
    private String videoImg;
    private Integer userId;
    private String serverInfo;

}
