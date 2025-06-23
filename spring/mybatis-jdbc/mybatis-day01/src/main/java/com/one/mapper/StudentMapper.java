package com.one.mapper;

import com.one.domain.Student;
import java.util.List;

/**
    持久层接口   Mybatis接口代理的开发方式,不用手动实现该接口
     Mapper 接口开发需要遵循以下规范：
        1) Mapper.xml文件中的namespace属性与mapper接口的全限定名相同
        2) Mapper接口方法名和Mapper.xml中定义的每个statement的id属性值相同
        3) Mapper接口方法的输入参数类型和mapper.xml中定义的每个sql的parameterType属性值的类型相同
        4) Mapper接口方法的输出参数类型和mapper.xml中定义的每个sql的resultType属性值的类型相同
 */
public interface StudentMapper {
    //查询全部
    public abstract List<Student> selectAll();

    //根据id查询
    public abstract Student selectById(Integer id);

    //新增数据
    public abstract Integer insert(Student stu);

    //修改数据
    public abstract Integer update(Student stu);

    //删除数据
    public abstract Integer delete(Integer id);

    //多条件查询
    public abstract List<Student> selectCondition(Student stu);

    //根据多个id查询
    public abstract List<Student> selectByIds(List<Integer> ids);
}
