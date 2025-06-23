package com.com.springbootmvc.controller;

import com.com.springbootmvc.entity.Order;
import com.com.springbootmvc.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author one
 * @description TODO
 * @date 2023-3-22
 */
@RestController
@RequestMapping("/upload")
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * 上传多个文件
     *
     * @param files files
     * @param order order
     */
    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") List<MultipartFile> files, Order order) {
        fileService.uploadFile(files, order);
        return "success";
    }

    /**
     * 解决大文件上传的方式：分片上传和断点续传
     * @param file 将上传的文件安装一定的规则进行分割，最后将分片合并成完整的文件
     */
    @PostMapping("/bigFile")
    public String uploadBigFile(@RequestParam Long chunkSize, @RequestParam Integer totalNumber, @RequestParam Long chunkNumber, @RequestParam String md5, @RequestParam MultipartFile file) {
        fileService.uploadBigFile(chunkSize, totalNumber, chunkNumber, md5, file);
        return "success";
    }
}
