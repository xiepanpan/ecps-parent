package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.dao.*;
import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbItemService;
import com.xiepanpan.ecps.service.TsPtlUserService;
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
 * describe: 用户登录
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Service
public class TsPtlUserServiceImpl implements TsPtlUserService {
    
    @Autowired
    private TsPtlUserDao tsPtlUserDao;

    @Override
    public TsPtlUser selectUserByUsernameAndPwd(Map<String, String> map) {
        return tsPtlUserDao.selectUserByUsernameAndPwd(map);
    }
}
