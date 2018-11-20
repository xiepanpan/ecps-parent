package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbShipAddrDao;
import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.model.EbSpecValue;
import com.xiepanpan.ecps.service.EbShipAddrService;
import com.xiepanpan.ecps.service.EbSkuService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Override
    public EbSku getSkuDetailByIdFromRedis(Long skuId) {
        //从redis中取sku数据
        Jedis jedis = new Jedis(ECPSUtils.readProp("redis_ip"), Integer.parseInt(ECPSUtils.readProp("redis_port")),100000);
        String skuPrice =jedis.hget("ebSku:"+skuId,"skuPrice");
        String stockInventory =jedis.hget("ebSku:"+skuId,"stockInventory");
        String marketPrice =jedis.hget("ebSku:"+skuId,"marketPrice");
        String itemId = jedis.hget("ebSku:" + skuId, "itemId");

        EbSku ebSku = new EbSku();
        ebSku.setSkuId(skuId);
        ebSku.setSkuPrice(new BigDecimal(skuPrice));
        ebSku.setMarketPrice(new BigDecimal(marketPrice));
        ebSku.setStockInventory(new Integer(stockInventory));
        ebSku.setItemId(new Long(itemId));
        //从redis中取sku所属的item数据
        String itemName = jedis.hget("ebSku:" + ebSku.getSkuId() + ":ebItem:" + itemId, "itemName");
        String itemNo = jedis.hget("ebSku:" + ebSku.getSkuId() + ":ebItem:" + itemId, "itemNo");
        String imgs = jedis.hget("ebSku:" + ebSku.getSkuId() + ":ebItem:" + itemId, "imgs");
        EbItem ebItem = new EbItem();
        ebItem.setItemId(new Long(itemId));
        ebItem.setItemName(itemName);
        ebItem.setItemNo(itemNo);
        ebItem.setImgs(imgs);

        ebSku.setEbItem(ebItem);

        //从redis中获取规格集合
        List<EbSpecValue> ebSpecValueList = new ArrayList<>();
        List<String> ebSpecIds = jedis.lrange("ebSku:" + ebSku.getSkuId() + ":ebSpecValueList", 0, -1);
        for (String ebSpecId:ebSpecIds) {
            EbSpecValue ebSpecValue = new EbSpecValue();
            String specValue = jedis.hget("ebSku:" + skuId + ":ebSpecValue:" + ebSpecId, "specValue");
            ebSpecValue.setSkuId(skuId);
            ebSpecValue.setSpecId(new Long(ebSpecId));
            ebSpecValue.setSpecValue(specValue);
            ebSpecValueList.add(ebSpecValue);
        }
        ebSku.setEbSpecValueList(ebSpecValueList);

        return ebSku;
    }
}
