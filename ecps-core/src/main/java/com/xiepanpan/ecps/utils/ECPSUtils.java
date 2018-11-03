package com.xiepanpan.ecps.utils;

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
}
