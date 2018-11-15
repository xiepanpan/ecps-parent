package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.TsPtlUser;

import java.util.List;
import java.util.Map;

/**
 * describe: 用户收货地址服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbShipAddrService {

    /**
     * 根据用户名密码查询用户信息
     * @param userId
     * @return
     */
    public List<EbShipAddr> selectAddrByUserId(Long userId);

}
