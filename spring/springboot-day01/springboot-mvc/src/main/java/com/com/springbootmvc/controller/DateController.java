package com.com.springbootmvc.controller;

import com.com.springbootmvc.entity.DateEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 测试前后端的日期时间交互
 */
@RestController
@RequestMapping()
public class DateController {

    @PostMapping("/date")
    public String uploadDate(@RequestBody DateEntity dateEntity) {
        ZonedDateTime endTime = dateEntity.getEndTime();
        System.out.println(endTime);
        endTime = endTime.withZoneSameInstant(ZoneId.of("UTC+8"));
        LocalDateTime localDateTime = endTime.toLocalDateTime();
        System.out.println(localDateTime);
        return "success";
    }

    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("GMT+8"));
        System.out.println(now);
        System.out.println(now.withZoneSameInstant(ZoneId.of("UTC")));
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        Date date = new Date();
        System.out.println(date);
    }
}
