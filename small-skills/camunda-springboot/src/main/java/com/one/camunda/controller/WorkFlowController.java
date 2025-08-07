package com.one.camunda.controller;

import io.jboot.web.controller.annotation.RequestMapping;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/workflow")
public class WorkFlowController {

    @Resource
    private RepositoryService repositoryService;

    @GetMapping("/deploy")
    public String deploy(){
        Deployment deploy = repositoryService.createDeployment()
                .name("部署第一个流程") // 部署名称
                .addClasspathResource("process.bpmn20.xml") // 绑定需要部署的流程文件
                .deploy(); // 部署流程
        return deploy.getId() + ":" + deploy.getName();
    }
}
