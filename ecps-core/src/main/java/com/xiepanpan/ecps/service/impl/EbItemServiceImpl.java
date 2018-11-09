package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.*;
import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbBrandService;
import com.xiepanpan.ecps.service.EbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe: 商品管理
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbItemServiceImpl implements EbItemService {
    
    @Autowired
    private EbItemDao ebItemDao;
    @Autowired
    private EbItemClobDao ebItemClobDao;
    @Autowired
    private EbParaValueDao ebParaValueDao;
    @Autowired
    private EbSkuDao ebSkuDao;


    @Override
    public Page selectItemByCondition(QueryCondition queryCondition) {
        //查询当前条件下的总记录数
        int totalCount = ebItemDao.selectItemByConditionCount(queryCondition);
        //创建page对象
        Page page = new Page();
        page.setPageNo(queryCondition.getPageNo());
        page.setTotalCount(totalCount);
        //计算startNum endNum
        queryCondition.setStartNum(page.getStartNum());
        queryCondition.setEndNum(page.getEndNum());
        List<EbItem> ebItemList = ebItemDao.selectItemByCondition(queryCondition);
        page.setList(ebItemList);
        return page;
    }

    /**
     * 保存商品信息  事务已配置
     * @param ebItem
     * @param ebItemClob
     * @param ebParaValueList
     * @param ebSkuList
     */
    @Override
    public void saveItem(EbItem ebItem, EbItemClob ebItemClob, List<EbParaValue> ebParaValueList, List<EbSku> ebSkuList) {
        ebItemDao.saveItem(ebItem);
        ebItemClobDao.saveItemClob(ebItemClob,ebItem.getItemId());
        ebParaValueDao.saveParaValue(ebParaValueList,ebItem.getItemId());
        ebSkuDao.saveSku(ebSkuList,ebItem.getItemId());
    }
}
