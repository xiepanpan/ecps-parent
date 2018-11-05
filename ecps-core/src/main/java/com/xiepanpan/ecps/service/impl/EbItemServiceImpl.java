package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.EbBrandDao;
import com.xiepanpan.ecps.dao.EbItemDao;
import com.xiepanpan.ecps.model.EbBrand;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.model.Page;
import com.xiepanpan.ecps.model.QueryCondition;
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
}
