package com.one.sharding;

import me.ahoo.cosid.IdGenerator;
import me.ahoo.cosid.machine.MachineId;
import me.ahoo.cosid.provider.IdGeneratorProvider;
import me.ahoo.cosid.segment.SegmentId;
import me.ahoo.cosid.snowflake.SnowflakeId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class DistIDTest {
    @Autowired
    private IdGeneratorProvider provider;
    @Autowired
    private MachineId machineId;
    @Autowired
    private SnowflakeId snowflakeId;
//    @Autowired
//    private SegmentId segmentId;

    @Test
    public void getId(){
        for (int i = 0; i < 100; i++) {
            System.out.println(provider.getShare().generate());
            //如果没有业务，那么所有雪花ID 都可以在同一毫秒内生成，工作序列位会严格递增。
            //但是，只要稍微休眠一下，工作序列位就无法保证严格递增了。
//            try {
//                Thread.sleep(1L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(segmentId.generate());
        }
    }

    @Test
    public void snowflakeIdTest(){
        System.out.println("机器 ID："+ machineId.getMachineId());
        for (int i = 0; i < 10; i++) {
            System.out.println("第"+i+"个机器 ID："+ machineId.getMachineId());
            System.out.println("snowflakeId："+ snowflakeId.generate());
            System.out.println("生成器生成的 ID："+ provider.getShare().generate());
        }
    }
}
