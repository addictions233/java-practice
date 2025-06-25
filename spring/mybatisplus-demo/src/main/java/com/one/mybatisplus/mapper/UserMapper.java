package com.one.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.one.mybatisplus.entity.TbUser;
import org.apache.ibatis.annotations.Param;

/**
 * @InterfaceName: UserMapper
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/23
 */

/**
 * @author one
 * 使用mybatis plus定义Mapper接口,需要让Mapper接口继承BaseMapper接口
 */
//@DS("db_test")
public interface UserMapper extends BaseMapper<TbUser> {
    IPage<TbUser> selectPageByCustom(@Param("page") Page<TbUser> page);
}
