package com.one.dao;


import com.one.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

/**
 * @ClassName StudentMapper
 * @Description TODO
 * @Author one
 * @Date 2021/8/28 11:47
 * @Version 1.0
 */
@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student")
    List<Student> getAll();

    @Update("UPDATE student SET name=#{name},age=#{age},teacher=#{teacher} WHERE id =#{id}")
    //@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    void update(Student student);
    
}
