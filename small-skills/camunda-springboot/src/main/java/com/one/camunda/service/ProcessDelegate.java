package com.one.camunda.service;

import org.camunda.bpm.engine.delegate.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProcessDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String taskId = execution.getId();
        String instanceId = execution.getProcessInstanceId();
        Map<String, Object> variables = execution.getVariables();
    }


    /**
     * 任务监听器用于在某个与任务相关的事件发生时执行自定义Java逻辑或表达式。它只能作为用户任务的子元素添加到流程定义中。
     *
     * 请注意，这也必须作为BPMN 2.0扩展元素的子级和Camunda命名空间中发生，因为任务侦听器是专门为Camunda引擎构建的。
     * 任务监听器可以在以下事件上触发：
     *
     * 分配：任务分配给用户或组时触发。
     * 完成：用户完成任务时触发。
     * 激活：任务从等待状态转换为活动状态时触发。
     * 挂起：任务被挂起时触发。
     * 取消：任务被取消时触发。
     * @return 任务监听器
     */
    @Bean
    TaskListener myTaskListener() {
        return delegateTask -> {

            String taskId = delegateTask.getId();
            String instanceId = delegateTask.getProcessInstanceId();

            Map<String, Object> variables = delegateTask.getVariables();
            // TODO: 20log/3/22
            delegateTask.setVariable("", "");
        };
    }

    /**
     * 执行监听器 - Execution Listener
     * 执行侦听器在流程执行过程中发生某些事件时执行外部Java代码或计算表达式。可以用在任何任务中，可以捕获的事件有：
     *
     * 流程实例的开始和结束。
     * 进行过渡。
     * 活动的开始和结束。
     * 网关的开始和结束。
     * 中间事件的开始和结束。
     * 结束开始事件或开始结束事件
     * 适用场景：每个任务结束时设置任务进度
     */
    @Component
    public static class ExampleExecutionListenerOne implements ExecutionListener {

        public void notify(DelegateExecution execution) throws Exception {
            execution.setVariable("variableSetInExecutionListener", "firstValue");
            execution.setVariable("eventReceived", execution.getEventName());
        }
    }

}