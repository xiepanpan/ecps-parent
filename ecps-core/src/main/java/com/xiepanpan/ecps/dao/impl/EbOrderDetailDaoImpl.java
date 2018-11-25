package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbOrderDetailDao;
import com.xiepanpan.ecps.model.EbOrderDetail;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class EbOrderDetailDaoImpl extends SqlSessionDaoSupport implements EbOrderDetailDao {

	String ns = "com.xiepanpan.ecps.mapper.EbOrderDetailMapper.";

	public void saveOrderDetail(EbOrderDetail detail) {
		this.getSqlSession().insert(ns+"insert", detail);
	}

	

}
