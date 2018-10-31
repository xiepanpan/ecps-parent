package com.xiepanpan.ecps.dao;

import com.xiepanpan.ecps.model.EbBrand;

import java.util.List;

/**
 * describe: 品牌管理dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbBrandDao {

    public void saveBrand(EbBrand ebBrand);

    public List<EbBrand> selectBrandAll();
}
