package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbShipAddrDao;
import com.xiepanpan.ecps.model.EbShipAddr;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Repository
public class EbShipAddrDaoImpl extends SqlSessionDaoSupport implements EbShipAddrDao {

    String ns="com.xiepanpan.ecps.mapper.EbShipAddrMapper.";


    @Override
    public List<EbShipAddr> selectAddrByUserId(Long userId) {
        return this.getSqlSession().selectList(ns+"selectAddrByUserId",userId);
    }

    @Override
    public EbShipAddr selectAddrById(Long addrId) {
        return this.getSqlSession().selectOne(ns+"selectByPrimaryKey",addrId);
    }

    @Override
    public void saveAddr(EbShipAddr ebShipAddr) {
        this.getSqlSession().insert(ns+"insert",ebShipAddr);
    }

    @Override
    public void updateAddr(EbShipAddr ebShipAddr) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",ebShipAddr);
    }

    @Override
    public void updateDefaultAddr(Long userId) {
        this.getSqlSession().update(ns+"updateDefaultAddr",userId);
    }
}
