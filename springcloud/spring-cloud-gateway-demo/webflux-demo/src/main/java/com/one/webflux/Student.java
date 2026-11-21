package com.one.webflux;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("tb_student")
public class Student {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private LocalDate birthday;

    private Integer classesId;
}
