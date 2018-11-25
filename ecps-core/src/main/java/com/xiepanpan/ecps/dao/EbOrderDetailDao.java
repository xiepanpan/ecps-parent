package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbOrderDetail;

public interface EbOrderDetailDao {

	/**
	 * 保存订单详情信息
	 * @param detail
	 */
	public void saveOrderDetail(EbOrderDetail detail);
}
