package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbConsoleLogDao;
import com.xiepanpan.ecps.dao.EbItemDao;
import com.xiepanpan.ecps.model.EbConsoleLog;
import com.xiepanpan.ecps.model.EbItem;
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
public class EbConsoleLogDaoImpl extends SqlSessionDaoSupport implements EbConsoleLogDao {

    String ns="com.xiepanpan.ecps.mapper.EbConsoleLogMapper.";


    /**
     * 保存后台操作日志
     * @param ebConsoleLog
     */
    @Override
    public void saveConsoleLog(EbConsoleLog ebConsoleLog) {
        this.getSqlSession().insert(ns+"insert",ebConsoleLog);
    }
}
