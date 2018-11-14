package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbItemDao;
import com.xiepanpan.ecps.dao.TsPtlUserDao;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.QueryCondition;
import com.xiepanpan.ecps.model.TsPtlUser;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Repository
public class TsPtlUserDaoImpl extends SqlSessionDaoSupport implements TsPtlUserDao {

    String ns="com.xiepanpan.ecps.mapper.TsPtlUserMapper.";


    @Override
    public TsPtlUser selectUserByUsernameAndPwd(Map<String, String> map) {
        return this.getSqlSession().selectOne(ns+"selectUserByUsernameAndPwd",map);
    }
}
