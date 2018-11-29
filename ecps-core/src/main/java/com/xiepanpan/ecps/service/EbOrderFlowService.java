package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbOrder;
import com.xiepanpan.ecps.model.EbOrderDetail;

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
}
