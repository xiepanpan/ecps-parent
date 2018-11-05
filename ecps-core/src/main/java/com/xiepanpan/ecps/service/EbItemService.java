package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbBrand;
import com.xiepanpan.ecps.model.Page;
import com.xiepanpan.ecps.model.QueryCondition;

import java.util.List;

/**
 * describe: 商品管理服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbItemService {

    public Page selectItemByCondition(QueryCondition queryCondition);
}
