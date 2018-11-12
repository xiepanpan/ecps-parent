package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.*;

import java.util.List;

/**
 * describe: 商品管理服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbItemService {

    public Page selectItemByCondition(QueryCondition queryCondition);

    public void saveItem(EbItem ebItem, EbItemClob ebItemClob,List<EbParaValue> ebParaValueList,List<EbSku> ebSkuList);

    /**
     * 审核商品
     * @param itemId
     * @param auditStatus
     * @param notes
     */
    public void auditItem(Long itemId,Short auditStatus,String notes);

    /**
     * 商品上下架
     * @param itemId
     * @param showStatus
     * @param notes
     */
    public void showItem(Long itemId, Short showStatus, String notes);
}
