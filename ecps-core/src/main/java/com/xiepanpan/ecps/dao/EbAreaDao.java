package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbArea;

import java.util.List;

/**
 * describe: 省市区dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbAreaDao {

    public List<EbArea> selectAreaByPid(Long areaId);


}
