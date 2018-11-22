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

    /**
     * 根据收货地址主键查询收货信息
     * @param addrId
     * @return
     */
    public EbShipAddr selectAddrById(Long addrId);

    /**
     * 从redis中根据地址Id查询收货地址信息
     * @param addrId
     * @return
     */
    public EbShipAddr selectAddrByIdFromRedis(Long userId,Long addrId);

    /**
     * 保存或更新地址信息
     * @param ebShipAddr
     */
    public void saveOrUpdateAddr(EbShipAddr ebShipAddr);

    /**
     * 从redis中通过用户Id查收货地址
     * @param userId
     * @return
     */
    public List<EbShipAddr> selectAddrByUserIdFromRedis(Long userId);

}
