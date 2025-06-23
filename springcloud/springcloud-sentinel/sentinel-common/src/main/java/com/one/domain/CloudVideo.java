package com.one.domain;


import lombok.Data;

import java.util.Date;

/**
 * @Description 用cloud_video表相映射的实体类
 * @Author one
 * @Version 1.0
 **/
@Data
public class CloudVideo {

    private Integer id;
    private String title;
    private String summary;
    private String coverImg;
    private Integer  price;
    private Date createTime;
    private Double point;
    private String serveInfo;

}
