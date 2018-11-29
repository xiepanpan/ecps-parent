package com.xiepanpan.ecps.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiepanpan.ecps.dao.EbOrderDao;
import com.xiepanpan.ecps.dao.EbOrderDetailDao;
import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.exception.EbStockException;
import com.xiepanpan.ecps.model.EbOrder;
import com.xiepanpan.ecps.model.EbOrderDetail;
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


}
