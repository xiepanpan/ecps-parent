package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbAreaDao;
import com.xiepanpan.ecps.model.EbArea;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.service.EbAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe: 省市区服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbAreaServiceImpl implements EbAreaService {
    
    @Autowired
    private EbAreaDao ebAreaDao;

    @Override
    public List<EbArea> selectAreaByPid(Long areaId) {
        return ebAreaDao.selectAreaByPid(areaId);
    }
}
