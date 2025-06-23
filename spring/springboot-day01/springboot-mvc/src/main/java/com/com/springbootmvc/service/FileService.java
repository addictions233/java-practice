package com.com.springbootmvc.service;

import com.com.springbootmvc.entity.Order;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileService {


    void uploadFile(List<MultipartFile> files, Order order);

    void uploadBigFile(Long chunkSize, Integer totalNumber, Long chunkNumber, String md5,  MultipartFile file);
}
