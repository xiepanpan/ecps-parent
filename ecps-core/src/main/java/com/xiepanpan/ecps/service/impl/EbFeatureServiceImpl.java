package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbFeatureDao;
import com.xiepanpan.ecps.dao.EbItemDao;
import com.xiepanpan.ecps.model.EbFeature;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.Page;
import com.xiepanpan.ecps.model.QueryCondition;
import com.xiepanpan.ecps.service.EbFeatureService;
import com.xiepanpan.ecps.service.EbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe: 商品属性业务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbFeatureServiceImpl implements EbFeatureService {
    
    @Autowired
    private EbFeatureDao ebFeatureDao;

    @Override
    public List<EbFeature> selectCommFeature() {
        return ebFeatureDao.selectCommFeature();
    }

    @Override
    public List<EbFeature> selectSpecFeature() {
        return ebFeatureDao.selectSpecFeature();
    }
}
