package com.com.springbootmvc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.context.annotation.Import;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class DateEntity {

    /**
     * 注解@DateTimeFormat区别‌：后者用于Spring MVC参数绑定（如表单提交），而@JsonFormat专用于JSON序列化‌
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startTime;

    // 前端传过来的时间是UTC时间，需要转换为东八区时间  2025-07-30T17:41:10Z
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private ZonedDateTime endTime;
}
