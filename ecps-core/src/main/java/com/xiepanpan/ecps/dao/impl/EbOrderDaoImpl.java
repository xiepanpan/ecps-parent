package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbOrderDao;
import com.xiepanpan.ecps.model.EbOrder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class EbOrderDaoImpl extends SqlSessionDaoSupport implements EbOrderDao {

	String ns = "com.xiepanpan.ecps.mapper.EbOrderMapper.";

	public void saveOrder(EbOrder order) {
		this.getSqlSession().insert(ns+"insert", order);
	}

	@Override
	public void updateOrder(EbOrder ebOrder) {
		this.getSqlSession().update(ns+"updateByPrimaryKeySelective",ebOrder);
	}

	@Override
	public EbOrder getOrderById(Long orderId) {
		return this.getSqlSession().selectOne(ns+"selectByPrimaryKey",orderId);
	}

	@Override
	public EbOrder selectOrderDetailById(Long orderId) {
		return this.getSqlSession().selectOne(ns+"selectOrderDetailById",orderId);
	}


}
