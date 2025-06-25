package com.one.mybatisplus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class PageController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/queryByPage")
    public Page<TbUser> queryByPage(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<TbUser> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize);
        IPage<TbUser> ipage = userMapper.selectPageByCustom(page);
        return new PageImpl<>(ipage.getRecords(), pageable, ipage.getTotal());
    }


}
