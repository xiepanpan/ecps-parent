package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbFeature;
import com.xiepanpan.ecps.model.Page;
import com.xiepanpan.ecps.model.QueryCondition;

import java.util.List;

/**
 * describe: 商品属性服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbFeatureService {

    public List<EbFeature> selectCommFeature();

    public List<EbFeature> selectSpecFeature();
}
