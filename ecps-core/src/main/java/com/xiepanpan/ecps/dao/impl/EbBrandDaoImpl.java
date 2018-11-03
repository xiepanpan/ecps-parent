package com.xiepanpan.ecps.dao.impl;

import com.xiepanpan.ecps.dao.EbBrandDao;
import com.xiepanpan.ecps.model.EbBrand;
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
public class EbBrandDaoImpl extends SqlSessionDaoSupport implements EbBrandDao {

    String ns="com.xiepanpan.ecps.mapper.EbBrandMapper.";
    /**
     * 保存品牌信息
     * @param ebBrand
     */
    @Override
    public void saveBrand(EbBrand ebBrand) {
        this.getSqlSession().insert(ns+"insert",ebBrand);
    }

    @Override
    public List<EbBrand> selectBrandAll() {
        return this.getSqlSession().selectList(ns+"selectBrandAll");
    }

    /**
     * 根据品牌名字查询是否有该品牌
     * @return
     */
    @Override
    public List<EbBrand> selectBrandByName(String brandName) {
        return this.getSqlSession().selectList(ns+"selectBrandByName",brandName);
    }
}
