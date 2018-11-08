package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbFeature;
import com.xiepanpan.ecps.model.QueryCondition;

import java.util.List;

/**
 * describe: 商品属性dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbFeatureDao {

    public List<EbFeature> selectCommFeature();

    public List<EbFeature> selectSpecFeature();


}
