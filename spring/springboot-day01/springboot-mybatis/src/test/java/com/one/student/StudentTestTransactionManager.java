package com.one.student;


import com.one.service.impl.StudentsTransactionThread;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @ClassName StudentTestTransactionManager
 * @Description TODO
 * @Author one
 * @Date 2021/8/29 9:43
 * @Version 1.0
 */
@SpringBootTest
public class StudentTestTransactionManager {

    @Autowired
    StudentsTransactionThread studentsTransactionThread;

    @Test
    void updateStudentWithThreadsAndTrans() throws InterruptedException {
        studentsTransactionThread.updateStudentWithThreadsAndTrans();
    }
}
