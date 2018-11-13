package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.*;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * describe: 商品搜索索引服务层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
public interface EbIndexService {

    /**
     * 导入索引
     */
    public void importIndex() throws Exception;

    /**
     * 添加索引
     */
    public void addIndex();

    /**
     * 删除索引
     */
    public void deleteIndex();
}
