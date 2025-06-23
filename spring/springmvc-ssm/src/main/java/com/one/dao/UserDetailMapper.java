package com.one.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.one.domain.UserDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: UserMapper
 * @Description: TODO
 * @Author: one
 * @Date: 2021/02/22
 */
@Mapper
public interface UserDetailMapper extends BaseMapper<UserDetail> {
}
