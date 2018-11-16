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

    @Override
    public EbShipAddr selectAddrById(Long addrId) {
        return ebShipAddrDao.selectAddrById(addrId);
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
}
