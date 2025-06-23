package com.one.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Fox
 *
 */
@Service
public class OrderCodeGenerator {

    private static int count = 0;

    /**
     * 生成订单号
     */
    public String getOrderCode(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return simpleDateFormat.format(new Date()) + "-" + ++count;
    }


}
