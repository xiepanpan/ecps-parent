package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbBrand;

import java.util.List;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbBrandService {

    public void saveBrand(EbBrand ebBrand);

    public List<EbBrand> selectBrandAll();
}
