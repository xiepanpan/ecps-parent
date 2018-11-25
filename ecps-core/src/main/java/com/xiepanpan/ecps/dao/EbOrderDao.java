package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbOrder;

public interface EbOrderDao {

	/**
	 * 保存订单信息
	 * @param order
	 */
	public void saveOrder(EbOrder order);

}
