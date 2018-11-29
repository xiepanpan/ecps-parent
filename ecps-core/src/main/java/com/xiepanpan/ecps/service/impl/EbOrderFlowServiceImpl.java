package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.model.TaskBean;
import com.xiepanpan.ecps.service.EbOrderFlowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EbOrderFlowServiceImpl implements EbOrderFlowService {

    @Autowired
	private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


	@Override
	public void deployOrderFlow() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("com/xiepanpan/ecps/activiti/OrderFlow.bpmn")
                .addClasspathResource("com/xiepanpan/ecps/activiti/OrderFlow.png");
        deploymentBuilder.deploy();
    }

    @Override
    public String startInstance(Long orderId) {
	    //开启流程绑定orderId
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OrderFlow", orderId + "");
        return processInstance.getId();
    }

    @Override
    public void completeTaskByPid(String processInstanceId, String outcome) {
	    //查询任务
        Task task = taskService.createTaskQuery().processDefinitionKey("OrderFlow")
                .processInstanceId(processInstanceId).singleResult();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("outcome",outcome);
        taskService.complete(task.getId(),map);
    }

    @Override
    public List<TaskBean> selectTaskByAssignee(String assignee) {
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("OrderFlow")
                .taskAssignee(assignee)
                .orderByTaskCreateTime()
                .desc().list();
        List<TaskBean> taskBeanList = new ArrayList<TaskBean>();
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        for (Task task:taskList) {
            TaskBean taskBean = new TaskBean();
            taskBean.setTask(task);
            ProcessInstance processInstance = processInstanceQuery.processInstanceId(task.getProcessInstanceId()).singleResult();
            taskBean.setBusinessKey(processInstance.getBusinessKey());
            taskBeanList.add(taskBean);
        }
        return taskBeanList;
    }
}
