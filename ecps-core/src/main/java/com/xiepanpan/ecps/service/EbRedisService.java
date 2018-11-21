package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbSku;

import java.util.List;


/**
 * describe: redis业务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbRedisService {

    /**
     * 导入最小销售单元到redis
     */
    public void importEbSkuToRedis();

    /**
     * 导入收货地址单元到redis
     */
    public void importEbShipAddrToRedis();

}
