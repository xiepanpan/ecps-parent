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
}
