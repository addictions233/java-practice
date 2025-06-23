package com.one.springsecuritydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.one.springsecuritydemo.domain.pojo.UserDetail;
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
