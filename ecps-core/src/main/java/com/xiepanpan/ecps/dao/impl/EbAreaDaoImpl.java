package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbAreaDao;
import com.xiepanpan.ecps.model.EbArea;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.model.EbSpecValue;
import org.apache.ibatis.session.SqlSession;
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
public class EbAreaDaoImpl extends SqlSessionDaoSupport implements EbAreaDao {

    String ns="com.xiepanpan.ecps.mapper.EbAreaMapper.";


    @Override
    public List<EbArea> selectAreaByPid(Long areaId) {
        return this.getSqlSession().selectList(ns+"selectAreaByPid",areaId);
    }
}
