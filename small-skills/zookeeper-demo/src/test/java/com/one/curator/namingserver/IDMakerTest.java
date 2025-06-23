package com.one.curator.namingserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author Fox
 */
@Slf4j
public class IDMakerTest {

    @Test
    public void testMarkId() throws Exception {
        com.one.curator.namingserver.IDMaker idMaker = new com.one.curator.namingserver.IDMaker();
        idMaker.init();
        String pathPrefix = "/idmarker/id-";
        //模拟5个线程创建id
        for(int i=0;i<5;i++){
            new Thread(()->{
                for (int j=0;j<10;j++){
                    String id = null;
                    try {
                        id = idMaker.makeId(pathPrefix);
                        log.info("线程{}第{}次创建id为{}",Thread.currentThread().getName(),
                                j,id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },"thread"+i).start();
        }

        Thread.sleep(Integer.MAX_VALUE);

    }
}
