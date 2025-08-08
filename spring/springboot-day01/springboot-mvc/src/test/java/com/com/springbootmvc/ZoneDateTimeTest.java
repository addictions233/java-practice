package com.com.springbootmvc;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.junit.jupiter.api.Test;

import javax.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

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
        System.out.println(objectMapper.writeValueAsString(entity));
    }

    @Data
    static class Entity {

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",timezone = "GMT+8")
        private ZonedDateTime zoneDateTime;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = DEFAULT_TIMEZONE)
        private LocalDateTime localDateTime;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = DEFAULT_TIMEZONE)
        private Date date;
    }
}
