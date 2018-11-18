package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.service.EbRedisService;
import com.xiepanpan.ecps.service.EbSkuService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * describe: redis业务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbRedisServiceImpl implements EbRedisService {

    @Autowired
    private EbSkuDao ebSkuDao;

    @Override
    public void importEbSkuToRedis() {
        Jedis jedis = new Jedis(ECPSUtils.readProp("redis_ip"), Integer.parseInt(ECPSUtils.readProp("redis_port")),100000);
        List<EbSku> ebSkuList = ebSkuDao.selectSkuList();
        for (EbSku ebSku: ebSkuList) {
            jedis.lpush("ebSkuList",ebSku.getSkuId()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"skuId",ebSku.getSkuId()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"itemId",ebSku.getItemId()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"skuPrice",ebSku.getSkuPrice()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"stockInventory",ebSku.getStockInventory()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"marketPrice",ebSku.getMarketPrice()+"");
        }
    }
}
