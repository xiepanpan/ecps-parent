package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbOrder;
import com.xiepanpan.ecps.model.EbOrderDetail;
import com.xiepanpan.ecps.model.TaskBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 */
public interface EbOrderFlowService {

    /**
     * 部署订单流程
     */
	public void deployOrderFlow();

    /**
     * 开启流程实例
     * @param orderId
     * @return
     */
	public String startInstance(Long orderId);

    /**
     * 根据流程id完成任务
     * @param processInstanceId
     * @param outcome
     */
	public void completeTaskByPid(String processInstanceId,String outcome);

    /**
     * 根据签收人查询任务
     * @param assignee
     * @return
     */
	public List<TaskBean> selectTaskByAssignee(String assignee);

    /**
     * 根据任务id查询任务
     * @param taskId
     * @return
     */
	public TaskBean selectTaskBeanByTaskId(String taskId);

    /**
     * 根据任务id和输出线完成任务
     * @param taskId
     * @param outcome
     */
	public void completeTaskByTid(String taskId,String outcome);
}
