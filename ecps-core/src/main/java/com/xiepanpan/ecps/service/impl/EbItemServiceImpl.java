package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.*;
import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbItemService;
import com.xiepanpan.ecps.stub.EbWSItemService;
import com.xiepanpan.ecps.stub.EbWSItemServiceService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private EbConsoleLogDao ebConsoleLogDao;


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

    /**
     * 审核商品
     * @param itemId
     * @param auditStatus
     * @param notes
     */
    @Override
    public void auditItem(Long itemId, Short auditStatus, String notes) {
        EbItem ebItem = new EbItem();
        ebItem.setItemId(itemId);
        ebItem.setAuditStatus(auditStatus);
        ebItemDao.updateItem(ebItem);

        EbConsoleLog ebConsoleLog = new EbConsoleLog();
        ebConsoleLog.setEntityId(itemId);
        ebConsoleLog.setEntityName("商品表");
        ebConsoleLog.setNotes(notes);
        ebConsoleLog.setOpTime(new Date());
        ebConsoleLog.setOpType("审核");
        ebConsoleLog.setTableName("EB_ITEM");
        ebConsoleLog.setUserId(1L);
        ebConsoleLogDao.saveConsoleLog(ebConsoleLog);
    }

    @Override
    public void showItem(Long itemId, Short showStatus, String notes) {
        EbItem ebItem = new EbItem();
        ebItem.setItemId(itemId);
        ebItem.setShowStatus(showStatus);
        ebItemDao.updateItem(ebItem);

        EbConsoleLog ebConsoleLog = new EbConsoleLog();
        ebConsoleLog.setEntityId(itemId);
        ebConsoleLog.setEntityName("商品表");
        ebConsoleLog.setNotes(notes);
        ebConsoleLog.setOpTime(new Date());
        ebConsoleLog.setOpType("上下架");
        ebConsoleLog.setTableName("EB_ITEM");
        ebConsoleLog.setUserId(1L);
        ebConsoleLogDao.saveConsoleLog(ebConsoleLog);
    }

    @Override
    public List<EbItem> listItemByIndex(String price, Long brandId, String keyWords, String paraVals) throws Exception {
        SolrServer solrServer = ECPSUtils.getSolrServer();
        SolrQuery solrQuery = new SolrQuery();
        //价格筛选 filter query
        if (StringUtils.isNotBlank(price)) {
            String[] priceArray = price.split("-");
            solrQuery.set("fq","sku_price:["+priceArray[0]+" TO "+priceArray[1]+"]");
        }
        //默认查询所有
        String queryStr="*:*";
        //品牌不为空
        if (brandId!=null) {
            queryStr="brand_id:"+brandId;
        }
        //关键字不为空
        if (StringUtils.isNotBlank(keyWords)) {
            if (StringUtils.equals(queryStr,"*:*")){
                queryStr="item_keywords:"+keyWords;
            }else {
                queryStr=queryStr+" AND item_keywords:"+keyWords;
            }
        }

        if (StringUtils.isNotBlank(paraVals)) {
            String[] paraValArray = paraVals.split(",");
            String paraValsQuery="";
            for (String paraVal:paraValArray) {
                paraValsQuery=paraValsQuery+"para_vals:"+paraVal+" AND ";
            }
            //去掉最后一个AND
            paraValsQuery = paraValsQuery.substring(0,paraValsQuery.lastIndexOf(" AND "));

            //拼接到查询条件中
            if (StringUtils.equals(queryStr,"*:*")){
                queryStr=paraValsQuery;
            }else {
                queryStr=queryStr+" AND "+paraValsQuery;
            }
        }
        solrQuery.setQuery(queryStr);
        solrQuery.setSort("id",SolrQuery.ORDER.desc);

        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_name");
        solrQuery.addHighlightField("promotion");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //查询索引库
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //获得查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<EbItem> ebItemList = new ArrayList<>();
        for (SolrDocument solrDocument :solrDocumentList) {
            String itemId = (String) solrDocument.getFieldValue("id");
            String itemName = (String) solrDocument.getFieldValue("item_name");
            String promotion = (String) solrDocument.getFieldValue("promotion");
            String imgs = (String) solrDocument.getFieldValue("imgs");
            String skuPrice =solrDocument.getFieldValue("sku_price").toString();

            //取高亮显示
            Map<String, List<String>> listMap = highlighting.get(itemId);
            if (listMap!=null) {
                List<String> itemNameList = listMap.get("item_name");
                if (itemNameList!=null&&itemNameList.size()>0) {
                    String hlStr = "";
                    for (String hl:itemNameList) {
                        hlStr=hlStr+hl;
                    }
                    itemName=hlStr;
                }
                List<String> promotionList = listMap.get("promotion");
                if (promotionList!=null&&promotionList.size()>0) {
                    String hlStr = "";
                    for (String hl:promotionList) {
                        hlStr=hlStr+hl;
                    }
                    promotion=hlStr;
                }
            }

            EbItem ebItem = new EbItem();
            ebItem.setItemId(new Long(itemId));
            ebItem.setItemName(itemName);
            ebItem.setPromotion(promotion);
            ebItem.setImgs(imgs);
            ebItem.setSkuPrice(new BigDecimal(skuPrice));
            ebItemList.add(ebItem);
        }

        return ebItemList;
    }

    @Override
    public EbItem selectItemDetailById(Long itemId) {
        return ebItemDao.selectItemDetailById(itemId);
    }

    @Override
    public String publishItem(Long itemId, String password) {
        //创建的服务访问点集合的对象
        EbWSItemServiceService ebWSItemServiceService = new EbWSItemServiceService();
        EbWSItemService ebWSItemService = ebWSItemServiceService.getEbWSItemServicePort();
        return ebWSItemService.publishItem(itemId,password);
    }
}
