package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbBrandDao;
import com.xiepanpan.ecps.model.EbBrand;
import com.xiepanpan.ecps.service.EbBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbBrandServiceImpl implements EbBrandService {
    
    @Autowired
    EbBrandDao ebBrandDao;

    /**
     * 保存品牌信息
     * @param ebBrand
     */
    @Override
    public void saveBrand(EbBrand ebBrand) {
        ebBrandDao.saveBrand(ebBrand);
    }

    /**
     * 查找所有的品牌
     * @return
     */
    @Override
    public List<EbBrand> selectBrandAll() {
        return ebBrandDao.selectBrandAll();
    }
}
