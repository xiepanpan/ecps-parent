package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbSku;


/**
 * describe: 最小销售单元业务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbSkuService {

    /**
     * 根据最小销售单元id查询最小销售单元信息
     * @param skuId
     * @return
     */
    public EbSku getSkuById(Long skuId);

}
