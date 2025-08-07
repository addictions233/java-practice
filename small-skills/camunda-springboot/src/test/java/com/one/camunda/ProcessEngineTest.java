package com.one.camunda;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试流程引擎, 通过ProcessEngine, 可以获取包含工作流/BPM方法中的各种服务
 * ProcessEngine和服务对象都是线程安全的
 */
@SpringBootTest
public class ProcessEngineTest {

    public void testProcessEngine(){
        ProcessEngineConfigurationImpl configuration = new StandaloneProcessEngineConfiguration()
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUrl("jdbc:mysql://localhost:3306/camunda?useSSL=false&serverTimezone=UTC")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_TRUE)
                .setHistory(ProcessEngineConfigurationImpl.HISTORY_FULL);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        // 部署流程定义
        processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("process.bpmn20.xml")
                .deploy();
    }
}
