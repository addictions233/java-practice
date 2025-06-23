package com.one.controller;

import com.one.pojo.User;
import com.one.service.impl.StudentsTransactionThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName StudentController
 * @Description TODO
 * @Author one
 * @Date 2021/8/29 10:28
 * @Version 1.0
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentsTransactionThread studentsTransactionThread;

    @GetMapping(value = "/update")
    public String update() throws InterruptedException {
        studentsTransactionThread.updateStudentWithThreadsAndTrans();
        return "OK";
    }


}
