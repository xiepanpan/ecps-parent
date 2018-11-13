package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.*;
import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbIndexService;
import com.xiepanpan.ecps.service.EbItemService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * describe: 商品管理
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class EbIndexServiceImpl implements EbIndexService {
    
    @Autowired
    private EbItemDao ebItemDao;

    @Override
    public void importIndex() throws Exception {
        List<EbItem> ebItemList = ebItemDao.selectIsSelectItemList();
        SolrServer solrServer = ECPSUtils.getSolrServer();
        for (EbItem ebItem:ebItemList) {
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.addField("id",ebItem.getItemId());
            solrInputDocument.addField("item_name",ebItem.getItemName());
            solrInputDocument.addField("brand_id",ebItem.getBrandId());
            solrInputDocument.addField("sku_price",ebItem.getSkuPrice());
            solrInputDocument.addField("promotion",ebItem.getPromotion());
            solrInputDocument.addField("imgs",ebItem.getImgs());
            solrInputDocument.addField("item_keywords",ebItem.getKeywords());

            String paraVals = "";
            for (EbParaValue ebParaValue:ebItem.getParaList()){
                paraVals = paraVals+ebParaValue.getParaValue()+" ";
            }
            solrInputDocument.addField("para_vals",paraVals);
            solrServer.add(solrInputDocument);
        }
    }

    @Override
    public void addIndex() {

    }

    @Override
    public void deleteIndex() {

    }
}
