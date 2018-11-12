package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbItemDao;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.QueryCondition;
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
public class EbItemDaoImpl extends SqlSessionDaoSupport implements EbItemDao {

    String ns="com.xiepanpan.ecps.mapper.EbItemMapper.";

    /**
     * 根据条件查询商品
     * @param queryCondition
     * @return
     */
    @Override
    public List<EbItem> selectItemByCondition(QueryCondition queryCondition) {
        return this.getSqlSession().selectList(ns+"selectItemByCondition",queryCondition);
    }

    /**
     * 根据条件查询商品总量
     * @param queryCondition
     * @return
     */
    @Override
    public Integer selectItemByConditionCount(QueryCondition queryCondition) {
        return this.getSqlSession().selectOne(ns+"selectItemByConditionCount",queryCondition);
    }

    @Override
    public void saveItem(EbItem ebItem) {
        this.getSqlSession().insert(ns+"insert",ebItem);
    }

    @Override
    public void updateItem(EbItem ebItem) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",ebItem);
    }
}
