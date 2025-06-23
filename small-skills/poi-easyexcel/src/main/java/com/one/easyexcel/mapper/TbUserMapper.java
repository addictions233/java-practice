package com.one.easyexcel.mapper;

import com.one.easyexcel.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName: TbUserMapper
 * @Description: TODO
 * @Author: one
 * @Date: 2021/06/05
 */
@Mapper
public interface TbUserMapper {
    /**
     * 查询所用
     * @return List<TbUser>
     */
    @Select("select * from tb_user")
    List<TbUser> findAll();
}
