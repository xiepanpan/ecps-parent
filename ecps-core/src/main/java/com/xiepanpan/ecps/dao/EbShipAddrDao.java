package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbShipAddr;

import java.util.List;

/**
 * describe: 用户收货地址dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbShipAddrDao {

    /**
     * 根据用户id查询收货地址
     * @param map
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
     * 新增收货地址信息
     * @param ebShipAddr
     */
    public void saveAddr(EbShipAddr ebShipAddr);

    /**
     * 更新收货地址信息
     * @param ebShipAddr
     */
    public void updateAddr(EbShipAddr ebShipAddr);

    public void updateDefaultAddr(Long userId);


}
