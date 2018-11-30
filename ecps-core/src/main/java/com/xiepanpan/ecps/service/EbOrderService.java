package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbOrder;
import com.xiepanpan.ecps.model.EbOrderDetail;
import com.xiepanpan.ecps.model.TaskBean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public interface EbOrderService {
	
	
	public String saveOrder(EbOrder order, List<EbOrderDetail> detailList, HttpServletRequest request, HttpServletResponse response);

    /**
     * 更新支付订单
     * @param processInstanceId
     * @param orderId
     * @return
     */
	public String updatePayOrder(String processInstanceId,Long orderId);

    /**
     * 根据任务签收人和是否外呼查询任务
     * @param assignee
     * @param isCall
     * @return
     */
	public List<TaskBean> selectTaskBeanByAssigneeAndIsCall(String assignee,Short isCall);

    /**
     * 根据签收人查询任务
     * @param assignee
     * @return
     */
    public List<TaskBean> selectTaskBeanByAssignee(String assignee);

    /**
     * 查询任务信息和订单详情
     * @param orderId
     * @param taskId
     * @return
     */
	public TaskBean selectTBOrderDetail(Long orderId,String taskId);

    /**
     * 外呼完成
     * @param orderId
     */
	public void completeCall(Long orderId);

    /**
     * 完成任务 update开头 开启事务
     * @param orderId
     * @param taskId
     * @param outcome
     */
	public void updateCompleteTask(Long orderId,String taskId,String outcome);
}
