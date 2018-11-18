package com.xiepanpan.ecps.ws.service.impl;

import com.xiepanpan.ecps.dao.EbItemDao;
import com.xiepanpan.ecps.model.EbItem;
import com.xiepanpan.ecps.utils.ECPSUtils;
import com.xiepanpan.ecps.utils.FMutil;
import com.xiepanpan.ecps.ws.service.EbWSItemService;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ecps-parent
 * @description:
 * @author: xiepanpan
 * @create: 2018-11-18 14:35
 **/
@Service
public class EbWSItemServiceImpl implements EbWSItemService {

    @Autowired
    private EbItemDao ebItemDao;

    @Override
    public String publishItem(Long itemId, String password) throws Exception {
        String result= "success";
        if (StringUtils.equals(password, ECPSUtils.readProp("ws_pwd"))) {
            EbItem ebItem = ebItemDao.selectItemDetailById(itemId);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("ebItem",ebItem);
            map.put("path",ECPSUtils.readProp("portal_path"));
            map.put("request_file_path",ECPSUtils.readProp("request_file_path"));
            FMutil.ouputFile("productDetail.ftl",ebItem.getItemId()+".html",map);
        }else {
            result="fail";
            return result;
        }
        return result;
    }
}
