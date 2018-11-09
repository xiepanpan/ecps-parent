package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbItemClobDao;
import com.xiepanpan.ecps.dao.EbItemDao;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.EbItemClob;
import com.xiepanpan.ecps.model.QueryCondition;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Repository
public class EbItemClobDaoImpl extends SqlSessionDaoSupport implements EbItemClobDao {

    String ns="com.xiepanpan.ecps.mapper.EbItemClobMapper.";

    @Override
    public void saveItemClob(EbItemClob ebItemClob,Long itemId) {
        ebItemClob.setItemId(itemId);
        this.getSqlSession().insert(ns+"insert",ebItemClob);
    }
}
