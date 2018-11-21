package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbShipAddrDao;
import com.xiepanpan.ecps.dao.EbSkuDao;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.model.EbSpecValue;
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
    @Autowired
    private EbShipAddrDao ebShipAddrDao;

    @Override
    public void importEbSkuToRedis() {
        Jedis jedis = new Jedis(ECPSUtils.readProp("redis_ip"), Integer.parseInt(ECPSUtils.readProp("redis_port")),100000);
//        List<EbSku> ebSkuList = ebSkuDao.selectSkuList();
          List<EbSku> ebSkuList = ebSkuDao.selectSkuDetailList();

        for (EbSku ebSku: ebSkuList) {
            jedis.lpush("ebSkuList",ebSku.getSkuId()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"skuId",ebSku.getSkuId()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"itemId",ebSku.getItemId()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"skuPrice",ebSku.getSkuPrice()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"stockInventory",ebSku.getStockInventory()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId(),"marketPrice",ebSku.getMarketPrice()+"");
            //同步商品信息
            jedis.hset("ebSku:"+ebSku.getSkuId()+":ebItem:"+ebSku.getEbItem().getItemId(),"itemId",ebSku.getEbItem().getItemId()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId()+":ebItem:"+ebSku.getEbItem().getItemId(),"itemName",ebSku.getEbItem().getItemName()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId()+":ebItem:"+ebSku.getEbItem().getItemId(),"itemNo",ebSku.getEbItem().getItemNo()+"");
            jedis.hset("ebSku:"+ebSku.getSkuId()+":ebItem:"+ebSku.getEbItem().getItemId(),"imgs",ebSku.getEbItem().getImgs()+"");
            //规格信息同步
            for (EbSpecValue ebSpecValue:ebSku.getEbSpecValueList()) {
                //存放规格id集合
                jedis.lpush("ebSku:"+ebSku.getSkuId()+":ebSpecValueList",ebSpecValue.getSpecId()+"");
                jedis.hset("ebSku:"+ebSku.getSkuId()+":ebSpecValue:"+ebSpecValue.getSpecId(),"specId",ebSpecValue.getSpecId()+"");
                jedis.hset("ebSku:"+ebSku.getSkuId()+":ebSpecValue:"+ebSpecValue.getSpecId(),"skuId",ebSpecValue.getSkuId()+"");
                jedis.hset("ebSku:"+ebSku.getSkuId()+":ebSpecValue:"+ebSpecValue.getSpecId(),"specValue",ebSpecValue.getSpecValue()+"");
            }
        }
    }

    /**
     * list类型 user:3002:addrList :[1002,1003]
     * hset    user:3002:addr:1002: [{shipAddrId,1002},{shipName,"张三"},,,]
     * 这里只导入一个用户的收货地址信息
     */
    @Override
    public void importEbShipAddrToRedis() {
        Jedis jedis = new Jedis(ECPSUtils.readProp("redis_ip"), Integer.parseInt(ECPSUtils.readProp("redis_port")),100000);
        List<EbShipAddr> ebShipAddrList = ebShipAddrDao.selectAddrByUserId((long) 3002);
        for (EbShipAddr ebShipAddr:ebShipAddrList) {
            jedis.lpush("user:3002:addrList",ebShipAddr.getShipAddrId()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"shipAddrId",ebShipAddr.getShipAddrId()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"shipName",ebShipAddr.getShipName()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"province",ebShipAddr.getProvince()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"city",ebShipAddr.getCity()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"district",ebShipAddr.getDistrict()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"zipCode",ebShipAddr.getZipCode()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"addr",ebShipAddr.getAddr()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"phone",ebShipAddr.getPhone()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"defaultAddr",ebShipAddr.getDefaultAddr()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"provText",ebShipAddr.getProvText()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"cityText",ebShipAddr.getCityText()+"");
            jedis.hset("user:3002:addr:"+ebShipAddr.getShipAddrId(),"distText",ebShipAddr.getDistText()+"");
        }
    }
}
