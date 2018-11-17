package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbShipAddrDao;
import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.service.EbShipAddrService;
import com.xiepanpan.ecps.service.EbSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe: 最小销售单元业务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbSkuServiceImpl implements EbSkuService {

    @Autowired
    private EbSkuDao ebSkuDao;

    @Override
    public EbSku getSkuById(Long skuId) {
        return ebSkuDao.getSkuById(skuId);
    }
}
