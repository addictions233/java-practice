package com.one.easyexcel.service.impl;

import com.one.easyexcel.service.IUserService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: UserServiceImpl
 * @Description: service层实现类
 * @Author: one
 * @Date: 2021/11/29
 */
public class UserServiceImpl implements IUserService {
    @Override
    public void excelExport(HttpServletResponse response) {

    }

    @Override
    public void excelImport(MultipartFile file) {

    }
}
