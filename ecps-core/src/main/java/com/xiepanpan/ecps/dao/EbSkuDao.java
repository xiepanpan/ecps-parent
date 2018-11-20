package com.xiepanpan.ecps.dao;


import com.xiepanpan.ecps.model.EbParaValue;
import com.xiepanpan.ecps.model.EbSku;

import java.util.List;

/**
 * describe: 商品最小销售单元dao层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbSkuDao {

    public void saveSku(List<EbSku> ebSkuList, Long itemId);

    /**
     * 根据最小销售单元id查询最小销售单元信息
     * @param skuId
     * @return
     */
    public EbSku getSkuById(Long skuId);

    public List<EbSku> selectSkuList();

    public List<EbSku> selectSkuDetailList();


}
