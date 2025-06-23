package com.one;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProcessEngineTest {

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 流程部署
     */
    @Test
    public void deployFlow(){
        Deployment deploy = processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("process/HolidayDemo1.bpmn20.xml") // 部署一个流程
                .name("第一个流程案例")
                .deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 发起流程
     */
    @Test
    public void startProcess(){
        // 发起流程需要通过RuntimeService来实现
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // act_re_procdef 表中的id
        String processId = "HolidayDemo1:1:89fad01e-7a42-11ee-b574-c03c59ad2248";
        // 根据流程定义Id启动 返回的是当前启动的流程实例 ProcessInstance
        //ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId);
        //System.out.println("processInstance.getId() = " + processInstance.getId());
        String processKey = "HolidayDemo1";
        runtimeService.startProcessInstanceByKey(processKey);
    }

    /**
     * 待办任务查询
     */
    @Test
    public void findTask(){
        // 任务查询这块我们可以通过 TaskService 来实现
        TaskService taskService = processEngine.getTaskService();
        // 查询的其实就是 act_ru_task 中的记录
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("zhangsan") // 根据审批人来查询
                .list();// 返回多条记录
        for (Task task : list) {
            System.out.println(task.getId());
        }
    }

    /**
     * 完成任务的审批
     */
    @Test
    public void completeTask(){
        TaskService taskService = processEngine.getTaskService();
        // 需要审批的任务 Id
        String taskId = "926b41f8-7a4c-11ee-97fd-c03c59ad2248";
        taskService.complete(taskId); // 通过complete方法完成审批
    }
}
