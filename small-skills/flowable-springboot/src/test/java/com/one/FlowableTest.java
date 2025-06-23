package com.one;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.junit.Test;

public class FlowableTest {

    /**
     * 不通过Spring容器。我们单独的构建ProcessEngine对象来实现部署的操作
     */
    @Test
    public void contextLoads() {
        // 1.流程引擎的配置
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                // 如果数据库中的表结构不存在就新建
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 2.构建流程引擎对象
        ProcessEngine processEngine = cfg.buildProcessEngine();
        System.out.println(processEngine);
        Deployment deploy = processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("process/HolidayDemo1.bpmn20.xml")
                .name("第一个流程案例")
                .deploy();
        System.out.println(deploy.getId());
    }


}
