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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private ZonedDateTime endTime;
}
