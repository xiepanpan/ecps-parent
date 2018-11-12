package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.QueryCondition;

import java.util.List;

/**
 * describe: 商品管理dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbItemDao {

    public List<EbItem> selectItemByCondition(QueryCondition queryCondition);

    public Integer selectItemByConditionCount(QueryCondition queryCondition);

    public void saveItem(EbItem ebItem);

    /**
     * 更新商品信息
     * @param ebItem
     */
    public void updateItem(EbItem ebItem);


}
