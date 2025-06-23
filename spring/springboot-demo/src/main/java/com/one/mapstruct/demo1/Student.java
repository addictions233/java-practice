package com.one.mapstruct.demo1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;

    private Integer age;


    public StudentVO convert() {
        return StudentVO.StudentMapper.INSTANCE.convert(this);
    }

    public StudentVO2 convert2() {
        return StudentVO2.StudentMapper2.INSTANCE.convert(this);
    }
}
