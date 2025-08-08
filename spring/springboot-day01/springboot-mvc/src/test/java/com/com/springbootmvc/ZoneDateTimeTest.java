package com.com.springbootmvc;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_TIMEZONE;

public class ZoneDateTimeTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testZoneDateTime() throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        Entity entity = new Entity();
        entity.setZoneDateTime(ZonedDateTime.now());
        entity.setLocalDateTime(LocalDateTime.now());
        entity.setDate(new Date());
        System.out.println("zoneDateTime：" + entity.getZoneDateTime());
        System.out.println("localDateTime：" + entity.getLocalDateTime());
        System.out.println("date:：" + entity.getDate());
        System.out.println(objectMapper.writeValueAsString(entity));
    }

    @Test
    public void testZoneDateTime2() throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        Entity entity = objectMapper.readValue(json, Entity.class);
        System.out.println(entity.getZoneDateTime());
        // 将ZoneDateTime转换为毫秒级别的时间戳
        System.out.println(entity.getZoneDateTime().toInstant().toEpochMilli());
        System.out.println(entity.getLocalDateTime());
        System.out.println(entity.getDate());
        // 将Date转换为毫秒级别的时间戳, 与ZoneDateTime的毫秒时间戳相同
        System.out.println(entity.getDate().toInstant().toEpochMilli());
    }

    String json = "{\"zoneDateTime\":\"2025-08-08T15:07:00.537Z\",\"localDateTime\":\"2025-08-08T23:07:00.537Z\",\"date\":\"2025-08-08T15:07:00.538Z\"}";

    @Data
    static class Entity {

        /**
         * 由于ZonedDateTime有时区信息，所以在序列化时会保留时区信息
         */
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT+8")
        private ZonedDateTime zoneDateTime;

        /**
         * 由于LocalDateTime没有时区信息，所以在序列化时会丢失时区信息, 所以指定timezone没有意义
         * 解决办法：
         * 1.将LocalDateTime转换为ZonedDateTime
         * 2.在序列化时指定时区
         */
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT+8")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime localDateTime;

        /**
         * date本质上表示的是毫秒级别的时间戳, 所以在序列化时指定时区,会转换为对应时区的时间戳
         * 反序列化时,会根据时区转换为毫秒级别的时间戳
         * 所以在反序列化时,需要指定时区,否则会根据默认时区转换
         */
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT+8")
        private Date date;
    }
}
