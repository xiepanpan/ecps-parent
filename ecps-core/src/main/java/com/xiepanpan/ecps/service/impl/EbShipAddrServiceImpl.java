package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbShipAddrDao;
import com.xiepanpan.ecps.dao.TsPtlUserDao;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.TsPtlUser;
import com.xiepanpan.ecps.service.EbShipAddrService;
import com.xiepanpan.ecps.service.TsPtlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ejb.EJBs;
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
}
