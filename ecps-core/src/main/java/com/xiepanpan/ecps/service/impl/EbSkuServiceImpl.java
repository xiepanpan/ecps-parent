package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbShipAddrDao;
import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.service.EbShipAddrService;
import com.xiepanpan.ecps.service.EbSkuService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
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

    @Override
    public EbSku getSkuByIdFromRedis(Long skuId) {
        Jedis jedis = new Jedis(ECPSUtils.readProp("redis_ip"), Integer.parseInt(ECPSUtils.readProp("redis_port")),100000);
        String skuPrice =jedis.hget("ebSku:"+skuId,"skuPrice");
        String stockInventory =jedis.hget("ebSku:"+skuId,"stockInventory");
        String marketPrice =jedis.hget("ebSku:"+skuId,"marketPrice");
        EbSku ebSku = new EbSku();
        ebSku.setSkuId(skuId);
        ebSku.setSkuPrice(new BigDecimal(skuPrice));
        ebSku.setMarketPrice(new BigDecimal(marketPrice));
        ebSku.setStockInventory(new Integer(stockInventory));
        return ebSku;
    }
}
