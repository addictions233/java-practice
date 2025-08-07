package com.one.camunda.service;

import io.jsonwebtoken.lang.Assert;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkFlowService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    /**
     * 开启流程
     */
    public void startProcess() {
        // 创建流程:同时创建第一个任务
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("key");
        System.out.println(instance.toString());
        // 流程暂停后，再执行相关任务会报错，需要先重新激活任务
        runtimeService.suspendProcessInstanceById(instance.getId());
        // 重新激活流程
        runtimeService.activateProcessInstanceById(instance.getId());
        // 删除流程
        runtimeService.deleteProcessInstance(instance.getId(), "手动删除");


    }

    /**
     * 查询流程定义
     */
    public List<ProcessDefinition> findProcesses() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

    /**
     * 查询任务
     */
    public List<Task> findTasks() {

        return taskService.createTaskQuery().list();
        // 分页查询当前任务
//        List<Task> list = taskService.createTaskQuery().orderByTaskCreateTime().desc().list();
    }

    public List<HistoricProcessInstance> findHistoryTask() {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
        return list;
    }

    /**
     * 任务回退:大体思路是拿到当前的任务，及当前任务的上一个历史任务，然后重启
     */
    public void rollBackTask(String taskId, String instanceId) {
        Task activeTask = taskService.createTaskQuery()
                .taskId(taskId)
                .active()
                .singleResult();
        List<HistoricTaskInstance> historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId)
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();

        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstance.stream().filter(v -> !v.getTaskDefinitionKey().equals(activeTask.getTaskDefinitionKey())).collect(Collectors.toList());

        Assert.notEmpty(historicTaskInstances, "当前已是初始任务！");
        HistoricTaskInstance curr = historicTaskInstances.get(0);

        runtimeService.createProcessInstanceModification(instanceId)
                .cancelAllForActivity(activeTask.getTaskDefinitionKey())
                .setAnnotation("重新执行")
                .startBeforeActivity(curr.getTaskDefinitionKey())
                .execute();

        //根据任务id设置执行人
        taskService.setAssignee(activeTask.getId(), "100010");

        // 用户信息设置
        identityService.setAuthenticatedUserId("100010");

        //获取用户信息设置
        Authentication authentication = identityService.getCurrentAuthentication();
    }

    /**
     * 测试流程编码
     */
    public void testParam(String key) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key, new HashMap<>());
        // 变量设置
        runtimeService.setVariable(instance.getId(), "param1", "value1");

        // 查询流程变量
        Object variable = runtimeService.getVariable(instance.getId(), "param1");
        System.out.println(variable);

        // 历史流程变量
        HistoricVariableInstance variableInstance = historyService.createHistoricVariableInstanceQuery().processInstanceId("instanceId").
                variableName("param1").singleResult();
        //变量值
        variableInstance.getValue();
        //变量名称
        variableInstance.getName();
    }


    /**
     * 配置表达式，可以实现JavaDelegate接口使用类名配置，快捷写法如下，
     * 比较推荐下面这种，此种可灵活配置bean和spring结合使用，注入service等业务方法
     */
    @Bean("customDelegate")
    public JavaDelegate customDelegate() {
        return execution -> {
            Map<String, Object> variables = execution.getVariables();
            Task task = taskService.createTaskQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
            //业务逻辑
            task.setOwner(String.valueOf("key"));
        };
    }

    @Component("monitorExecution")
    public class MonitorExecution {
        public void execution(DelegateExecution execution){
            String processInstanceId = execution.getProcessInstanceId();
        }
    }
}
