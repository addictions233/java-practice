package com.one.mapstruct.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {

    private String name;

    private Integer age;




    @Mapper
    public interface StudentMapper{
        StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

        StudentVO convert(Student student);
    }
}
