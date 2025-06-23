package com.com.springbootmvc.service.impl;

import ch.qos.logback.core.util.FileUtil;
import com.com.springbootmvc.entity.Order;
import com.com.springbootmvc.service.FileService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author one
 * @description SpringBoot上传文件
 * @date 2023-3-22
 */
@Service
public class FileServiceImpl implements FileService {
    /**
     * 测试异步线程中流是否会关闭
     *
     * @param files 文件
     * @param order 对象
     */
//    @Override
//    public void uploadFile(List<MultipartFile> files, Order order) {
//        System.out.println(order);
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(() -> {
//            for (MultipartFile file : files) { // 不能在异步线程中直接处理multipartFile对象,存在流关闭的问题
//                String originalFilename = file.getOriginalFilename();
//                try (FileOutputStream fileOutputStream = new FileOutputStream(originalFilename);
//                     InputStream inputStream = file.getInputStream()) {
//                    byte[] buffer = new byte[10];
//                    int n;
//                    while ((n = inputStream.read(buffer)) != -1) {
//                        fileOutputStream.write(buffer, 0, n);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    public void uploadFile(List<MultipartFile> files, Order order) {
        System.out.println(order);
        List<InputStream> streamList = files.stream().map(file -> {
            try {
                return file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            for (InputStream inputStream : streamList) {
                String originalFilename = System.currentTimeMillis() + ".jpg";
                try (FileOutputStream fileOutputStream = new FileOutputStream(originalFilename)) {
                    byte[] buffer = new byte[10];
                    int n;
                    while ((n = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, n);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public static final String UPLOAD_PATH = "D:\\upload\\";

    /**
     * 大文件的上传 : https://blog.csdn.net/qq_41158114/article/details/134333421
     * @param file
     */
    @Override
    public void uploadBigFile(Long chunkSize, Integer totalNumber, Long chunkNumber, String md5, MultipartFile file)  {
        //文件存放位置
        String dstFile = String.format("%s\\%s\\%s.%s", UPLOAD_PATH, md5, md5, StringUtils.getFilenameExtension(file.getOriginalFilename()));
        //上传分片信息存放位置
        String confFile = String.format("%s\\%s\\%s.conf", UPLOAD_PATH, md5, md5);
        //第一次创建分片记录文件
        //创建目录
        File dir = new File(dstFile).getParentFile();
        if (!dir.exists()) {
            dir.mkdir();
            //所有分片状态设置为0
            byte[] bytes = new byte[totalNumber];
            try {
                Files.write(Paths.get(confFile), bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //随机分片写入文件
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(dstFile, "rw");
             RandomAccessFile randomAccessConfFile = new RandomAccessFile(confFile, "rw");
             InputStream inputStream = file.getInputStream()) {
            //定位到该分片的偏移量
            randomAccessFile.seek(chunkNumber * chunkSize);
            //写入该分片数据
            randomAccessFile.write(file.getBytes());
            //定位到当前分片状态位置
            randomAccessConfFile.seek(chunkNumber);
            //设置当前分片上传状态为1
            randomAccessConfFile.write(1);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取文件分片状态，检测文件MD5合法性
     *
     * @param md5
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkFile")
    public ResponseEntity<Map<String, String>> uploadBig(@RequestParam String md5) throws Exception {
        Map<String, String> result = new HashMap<>();
        String uploadPath = String.format("%s\\%s\\%s.conf", UPLOAD_PATH, md5, md5);
        Path path = Paths.get(uploadPath);
        //MD5目录不存在文件从未上传过
        if (!Files.exists(path.getParent())) {
            result.put("msg", "文件未上传");
            return ResponseEntity.ok(result);
        }
        //判断文件是否上传成功
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = Files.readAllBytes(path);
        for (byte b : bytes) {
            stringBuilder.append(String.valueOf(b));
        }
        //所有分片上传完成计算文件MD5
        if (!stringBuilder.toString().contains("0")) {
            File file = new File(String.format("%s\\%s\\", UPLOAD_PATH, md5));
            File[] files = file.listFiles();
            String filePath = "";
            for (File f : files) {
                //计算文件MD5是否相等
                if (!f.getName().contains("conf")) {
                    filePath = f.getAbsolutePath();
                    try (InputStream inputStream = new FileInputStream(f)) {
                        String md5pwd = DigestUtils.md5DigestAsHex(inputStream);
                        if (!md5pwd.equalsIgnoreCase(md5)) {
                            return ResponseEntity.ok(result);
                        }
                    }
                }
            }
            result.put("path", filePath);
            return ResponseEntity.ok(result);
        } else {
            //文件未上传完成，反回每个分片状态，前端将未上传的分片继续上传
            result.put("chucks", stringBuilder.toString());
            return ResponseEntity.ok(result);
        }

    }
}
