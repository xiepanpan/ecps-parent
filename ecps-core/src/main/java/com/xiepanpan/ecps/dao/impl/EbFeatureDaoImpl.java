package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbFeatureDao;
import com.xiepanpan.ecps.model.EbFeature;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe: 规格dao
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Repository
public class EbFeatureDaoImpl extends SqlSessionDaoSupport implements EbFeatureDao {

    String ns="com.xiepanpan.ecps.mapper.EbFeatureMapper.";


    @Override
    public List<EbFeature> selectCommFeature() {
        return this.getSqlSession().selectList(ns+"selectCommFeature");
    }

    @Override
    public List<EbFeature> selectSpecFeature() {
        return this.getSqlSession().selectList(ns+"selectSpecFeature");
    }
}
