package com.one.mapstruct.demo1;

import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.runtime.ScriptObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO2 {

    private String nameVO;

    private Integer ageVO;

    @Mapper
    public interface StudentMapper2 {
        StudentMapper2 INSTANCE = Mappers.getMapper(StudentMapper2.class);

        @Mappings({@Mapping(source = "name", target = "nameVO"), @Mapping(source = "age", target = "ageVO")})
        StudentVO2 convert(Student student);
    }
}
