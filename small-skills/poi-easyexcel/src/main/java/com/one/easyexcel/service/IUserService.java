package com.one.easyexcel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @InterfaceName: IUserService
 * @Description: 业务功能接口
 * @Author: one
 * @Date: 2021/06/05
 */
public interface IUserService {

    /**
     * excel导出功能
     * @param response 返回
     */
    public void excelExport(HttpServletResponse response);

    /**
     * excel导入功能
     * @param file 导入文件
     */
    public void excelImport(MultipartFile file);
}
