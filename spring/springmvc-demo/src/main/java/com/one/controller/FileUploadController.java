package com.one.controller;

import com.one.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: FileUploadController
 * @Description: 接口中获取上传的文件
 * @Author: one
 * @Date: 2020/12/09
 */
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {
    /**
     * 在springmvc中实现文件上传
     *
     * @param file 文件
     * @param request 请求对象
     * @return String
     * @throws IOException
     */
    @RequestMapping("/file")
    public String fileUpload(MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("file upload is running....");
        //获取图片存储目录
        String path = request.getServletContext().getRealPath("images");
        file.transferTo(new File(path, Objects.requireNonNull(file.getOriginalFilename())));
        return "page.jsp";
    }

    /**
     * 使用@RequestParam封装文件上传的请求参数
     *
     * @param file 文件
     * @param name 参数
     * @return String
     */
    @RequestMapping(method = RequestMethod.POST, path = "/name")
    public String fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        System.out.println(file.getOriginalFilename());
        System.out.println(name);
        return "abc";
    }

    /**
     * 实现多个文件,多个参数的上传
     *
     * @param multipartFiles 多个文件
     * @param user 只要前端传参的key值和user对象的属性名相同,就将参数值封装到对应的属性值中
     * @return String
     */
    @RequestMapping(method = RequestMethod.POST, path = "/user")
    @ResponseBody
    public String fileUpload(@RequestParam("file") List<MultipartFile> multipartFiles, User user) {
        return "efg";
    }


}
