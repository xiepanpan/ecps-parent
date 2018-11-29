package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbOrder;

public interface EbOrderDao {

	/**
	 * 保存订单信息
	 * @param order
	 */
	public void saveOrder(EbOrder order);

    /**
     * 更新订单
     * @param ebOrder
     */
	public void updateOrder(EbOrder ebOrder);

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
	public EbOrder getOrderById(Long orderId);

}
