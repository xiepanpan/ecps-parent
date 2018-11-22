package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbShipAddrDao;
import com.xiepanpan.ecps.dao.TsPtlUserDao;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.TsPtlUser;
import com.xiepanpan.ecps.service.EbShipAddrService;
import com.xiepanpan.ecps.service.TsPtlUserService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.ejb.EJBs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * describe: 用户收货地址服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbShipAddrServiceImpl implements EbShipAddrService {
    
    @Autowired
    private EbShipAddrDao ebShipAddrDao;

    @Override
    public List<EbShipAddr> selectAddrByUserId(Long userId) {
        return ebShipAddrDao.selectAddrByUserId(userId);
    }

    @Override
    public EbShipAddr selectAddrById(Long addrId) {
        return ebShipAddrDao.selectAddrById(addrId);
    }

    @Override
    public EbShipAddr selectAddrByIdFromRedis(Long userId,Long addrId) {
        Jedis jedis = new Jedis(ECPSUtils.readProp("redis_ip"),new Integer(ECPSUtils.readProp("redis_port")));
        String shipName = jedis.hget("user:" + userId + ":addr:" + addrId, "shipName");
        String province = jedis.hget("user:" + userId + ":addr:" + addrId, "province");
        String city = jedis.hget("user:" + userId + ":addr:" + addrId, "city");
        String district = jedis.hget("user:" + userId + ":addr:" + addrId, "district");
        String addr = jedis.hget("user:" + userId + ":addr:" + addrId, "addr");
        String zipCode = jedis.hget("user:" + userId + ":addr:" + addrId, "zipCode");
        String phone = jedis.hget("user:" + userId + ":addr:" + addrId, "phone");
        String defaultAddr = jedis.hget("user:" + userId + ":addr:" + addrId, "defaultAddr");
        String provText = jedis.hget("user:" + userId + ":addr:" + addrId, "provText");
        String cityText = jedis.hget("user:" + userId + ":addr:" + addrId, "cityText");
        String distText = jedis.hget("user:" + userId + ":addr:" + addrId, "distText");
        EbShipAddr ebShipAddr = new EbShipAddr();
        ebShipAddr.setShipAddrId(new Long(addrId));
        ebShipAddr.setShipName(shipName);
        ebShipAddr.setProvince(province);
        ebShipAddr.setCity(city);
        ebShipAddr.setDistrict(district);
        ebShipAddr.setAddr(addr);
        ebShipAddr.setZipCode(zipCode);
        ebShipAddr.setPhone(phone);
        ebShipAddr.setDefaultAddr(new Short(defaultAddr));
        ebShipAddr.setProvText(provText);
        ebShipAddr.setCityText(cityText);
        ebShipAddr.setDistText(distText);
        return ebShipAddr;
    }

    @Override
    public void saveOrUpdateAddr(EbShipAddr ebShipAddr) {

        if (ebShipAddr.getDefaultAddr()==1) {
            //如何选中设置为默认地址 其他的默认地址都要进行取消默认
            ebShipAddrDao.updateDefaultAddr(ebShipAddr.getPtlUserId());
        }

        if (ebShipAddr.getShipAddrId()==null) {
            //新增的收货地址
            ebShipAddrDao.saveAddr(ebShipAddr);
        }else {
            //更新收货地址
            ebShipAddrDao.updateAddr(ebShipAddr);
        }
    }

    @Override
    public List<EbShipAddr> selectAddrByUserIdFromRedis(Long userId) {
        Jedis jedis = new Jedis(ECPSUtils.readProp("redis_ip"),new Integer(ECPSUtils.readProp("redis_port")));
        List<EbShipAddr> ebShipAddrList = new ArrayList<EbShipAddr>();
        List<String> addrIds = jedis.lrange("user:" + userId + ":addrList", 0, -1);
        for (String addrId:addrIds) {
            String shipName = jedis.hget("user:" + userId + ":addr:" + addrId, "shipName");
            String province = jedis.hget("user:" + userId + ":addr:" + addrId, "province");
            String city = jedis.hget("user:" + userId + ":addr:" + addrId, "city");
            String district = jedis.hget("user:" + userId + ":addr:" + addrId, "district");
            String addr = jedis.hget("user:" + userId + ":addr:" + addrId, "addr");
            String zipCode = jedis.hget("user:" + userId + ":addr:" + addrId, "zipCode");
            String phone = jedis.hget("user:" + userId + ":addr:" + addrId, "phone");
            String defaultAddr = jedis.hget("user:" + userId + ":addr:" + addrId, "defaultAddr");
            String provText = jedis.hget("user:" + userId + ":addr:" + addrId, "provText");
            String cityText = jedis.hget("user:" + userId + ":addr:" + addrId, "cityText");
            String distText = jedis.hget("user:" + userId + ":addr:" + addrId, "distText");
            EbShipAddr ebShipAddr = new EbShipAddr();
            ebShipAddr.setShipAddrId(new Long(addrId));
            ebShipAddr.setShipName(shipName);
            ebShipAddr.setProvince(province);
            ebShipAddr.setCity(city);
            ebShipAddr.setDistrict(district);
            ebShipAddr.setAddr(addr);
            ebShipAddr.setZipCode(zipCode);
            ebShipAddr.setPhone(phone);
            ebShipAddr.setDefaultAddr(new Short(defaultAddr));
            ebShipAddr.setProvText(provText);
            ebShipAddr.setCityText(cityText);
            ebShipAddr.setDistText(distText);
            ebShipAddrList.add(ebShipAddr);
        }

        return ebShipAddrList;
    }
}
