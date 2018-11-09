package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.EbItemClob;
import com.xiepanpan.ecps.model.QueryCondition;

import java.util.List;

/**
 * describe: 商品大字段dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbItemClobDao {

    public void saveItemClob(EbItemClob ebItemClob,Long itemId);


}
