package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbParaValue;

import java.util.List;

/**
 * describe: 商品参数dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbParaValueDao {

    public void saveParaValue(List<EbParaValue> ebParaValueList, Long itemId);


}
