package com.xiepanpan.ecps.service.impl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiepanpan.ecps.dao.EbOrderDao;
import com.xiepanpan.ecps.dao.EbOrderDetailDao;
import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.exception.EbStockException;
import com.xiepanpan.ecps.model.EbOrder;
import com.xiepanpan.ecps.model.EbOrderDetail;
import com.xiepanpan.ecps.model.TaskBean;
import com.xiepanpan.ecps.service.EbCartService;
import com.xiepanpan.ecps.service.EbOrderFlowService;
import com.xiepanpan.ecps.service.EbOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EbOrderServiceImpl implements EbOrderService {

	
	@Autowired
	private EbSkuDao skuDao;
	
	@Autowired
	private EbOrderDao orderDao;
	
	@Autowired
	private EbOrderDetailDao orderDetailDao;
	
	@Autowired
	private EbCartService ebCartService;
	@Autowired
    private EbOrderFlowService ebOrderFlowService;

	public String saveOrder(EbOrder order, List<EbOrderDetail> detailList, HttpServletRequest request, HttpServletResponse response) {
		orderDao.saveOrder(order);
		Map<String,Object> map = new HashMap<String,Object>();
		for(EbOrderDetail detail: detailList){
			detail.setOrderId(order.getOrderId());
			orderDetailDao.saveOrderDetail(detail);
			map.put("skuId", detail.getSkuId());
			map.put("quantity", detail.getQuantity());
			//更新数据库的库存
			int flag = skuDao.updateStock(map);
			if(flag == 0){
				throw new EbStockException();
			}
			//更新redis中的库存
			skuDao.updateStockRedis(map);
			//扣减库存
			/**
			EbSku sku = skuDao.getSkuById(detail.getSkuId());
			sku.setStockInventory(sku.getStockInventory() - detail.getQuantity());
			int flag = skuDao.update(sku);
			if(flag == 0){
				EbSku sku = skuDao.getSkuById(detail.getSkuId());
				if(sku.getStock() < detail.getQuantity()){
					throw EbStockException();
				}else{
					throw EbCurrException();
				}
		
			}
			**/
		}

		ebCartService.clearCart(request, response);
		//开启流程
        String processInstanceId = ebOrderFlowService.startInstance(order.getOrderId());
        return processInstanceId;
    }

    @Override
    public String updatePayOrder(String processInstanceId, Long orderId) {
	    EbOrder ebOrder = new EbOrder();
	    ebOrder.setOrderId(orderId);
	    ebOrder.setIsPaid((short) 1);
	    //更新订单状态
	    orderDao.updateOrder(ebOrder);
	    ebOrderFlowService.completeTaskByPid(processInstanceId,"付款");
        return null;
    }

    @Override
    public List<TaskBean> selectTaskBeanByAssigneeAndIsCall(String assignee, Short isCall) {
        List<TaskBean> taskBeanList = ebOrderFlowService.selectTaskByAssignee(assignee);
        List<TaskBean> taskBeanList1 = new ArrayList<TaskBean>();
        for (TaskBean taskBean:taskBeanList) {
            String businessKey = taskBean.getBusinessKey();
            EbOrder ebOrder = orderDao.getOrderById(Long.valueOf(businessKey));
            if (ebOrder.getIsCall().shortValue()==isCall.shortValue()) {
                taskBean.setEbOrder(ebOrder);
                taskBeanList1.add(taskBean);
            }
        }
        return taskBeanList1;
    }

    @Override
    public List<TaskBean> selectTaskBeanByAssignee(String assignee) {
        List<TaskBean> taskBeanList = ebOrderFlowService.selectTaskByAssignee(assignee);
        for (TaskBean taskBean:taskBeanList) {
            String businessKey = taskBean.getBusinessKey();
            EbOrder ebOrder = orderDao.getOrderById(Long.valueOf(businessKey));
            taskBean.setEbOrder(ebOrder);
        }
        return taskBeanList;
    }

    @Override
	public TaskBean selectTBOrderDetail(Long orderId, String taskId) {
        EbOrder ebOrder = orderDao.selectOrderDetailById(orderId);
        TaskBean taskBean = ebOrderFlowService.selectTaskBeanByTaskId(taskId);
        taskBean.setEbOrder(ebOrder);
        return taskBean;
	}

    @Override
    public void completeCall(Long orderId) {
        EbOrder ebOrder = new EbOrder();
        ebOrder.setOrderId(orderId);
        //设置为以外呼
        ebOrder.setIsCall((short) 1);
        orderDao.updateOrder(ebOrder);
    }

    @Override
    public void updateCompleteTask(Long orderId, String taskId, String outcome) {
        EbOrder ebOrder = new EbOrder();
        ebOrder.setOrderId(orderId);
        ebOrder.setUpdateTime(new Date());
        orderDao.updateOrder(ebOrder);
        //完成任务
        ebOrderFlowService.completeTaskByTid(taskId,outcome);
    }


}
