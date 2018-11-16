package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbArea;

import java.util.List;

/**
 * describe: 省市区服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbAreaService {

    /**
     * 根据省id查询地市信息
     * @param areaId
     * @return
     */
    public List<EbArea> selectAreaByPid(Long areaId);

}
