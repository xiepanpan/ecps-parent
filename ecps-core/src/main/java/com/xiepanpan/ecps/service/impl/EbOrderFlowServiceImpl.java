package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.model.TaskBean;
import com.xiepanpan.ecps.service.EbOrderFlowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
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

    @Override
    public TaskBean selectTaskBeanByTaskId(String taskId) {
	    TaskBean taskBean = new TaskBean();
        Task task = taskService.createTaskQuery().
                processDefinitionKey("OrderFlow")
                .taskId(taskId)
                .singleResult();
        taskBean.setTask(task);
        List<String> outcomes = this.getOutcomes(task);
        taskBean.setOutcome(outcomes);
        return taskBean;
    }

    @Override
    public void completeTaskByTid(String taskId, String outcome) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("outcome",outcome);
        taskService.complete(taskId,map);
    }

    /**
     * 得到所有的输出线信息
     * @param task
     * @return
     */
    public List<String> getOutcomes(Task task) {
        List<String> outcomeList = new ArrayList<String>();
	    //获得流程定义对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("OrderFlow")
                .processInstanceId(task.getProcessInstanceId()).singleResult();
        ActivityImpl activity = processDefinitionEntity.findActivity(processInstance.getActivityId());
        List<PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
        for (PvmTransition pvmTransition:outgoingTransitions) {
            String outcome = (String) pvmTransition.getProperty("name");
            outcomeList.add(outcome);
        }
        return outcomeList;
    }
}
