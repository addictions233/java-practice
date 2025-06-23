package com.one.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import com.one.mybatisplus.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServiceImpl
 * @Description: service层的实现类需要基础mybatis plus提供的ServiceImpl类
 * @Author: one
 * @Date: 2022/04/14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, TbUser> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 通过id查询一条User数据
     *
     * @param id 主键id
     * @return com.one.mybatisplusspringboot.pojo.User
     */
    @Override
    public TbUser selectOneUser(Long id) {
        LambdaQueryWrapper<TbUser> lambdaQueryWrapper = Wrappers.<TbUser>lambdaQuery().eq(TbUser::getId, id);
        TbUser user = this.getOne(lambdaQueryWrapper);
        return user;
    }

    /**
     * 使用mybatis plus在service层进行分页查询
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页条数
     * @return List<User>
     */
    @Override
    public List<TbUser> findUserByPage(Integer pageNumber, Integer pageSize) {
        return null;
    }

    /**
     * 通过注解指定查询数据源
     */
    @DS("#session.rw")
    public List<?> selectUser() {
        return jdbcTemplate.queryForList("select * from tb_user");
    }

    @DS("write")
    @DSTransactional
    public void createUser(TbUser user) {
        jdbcTemplate.update("insert into tb_user values(?,?,?,?,?)", user.getUserName(), user.getUserStatus(), user.getName(), user.getInfo());
        int i = 1/0;
    }
}
