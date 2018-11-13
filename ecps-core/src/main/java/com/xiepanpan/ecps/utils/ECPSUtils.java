package com.xiepanpan.ecps.utils;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * describe:
 *
 * @author xiepanpan
 * @date 2018/11/01
 */
public class ECPSUtils {

    private static SolrServer solrServer;

    /**
     * 读取配置文件
     * @param key
     * @return
     */
    public static String readProp(String key) {
        InputStream inputStream = ECPSUtils.class.getClassLoader()
                .getResourceAsStream("ecps_prop.properties");
        Properties properties = new Properties();
        String value= null;
        try {
            properties.load(inputStream);
            value= properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static SolrServer getSolrServer() {
        if (solrServer!=null) {
            return  solrServer;
        }else {
            String solrPath = ECPSUtils.readProp("solr_path");
            solrServer = new HttpSolrServer(solrPath);
            return  solrServer;
        }
    }
}
