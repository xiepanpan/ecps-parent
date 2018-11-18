package com.xiepanpan.ecps.ws.service;

import javax.jws.WebService;

/**
 * @program: ecps-parent
 * @description: webService 服务层
 * @author: xiepanpan
 * @create: 2018-11-18 12:27
 **/
@WebService
public interface EbWSItemService {

    /**
     * 发布商品
     * @param itemId
     * @param password
     * @return
     */
    public String publishItem(Long itemId,String password) throws Exception;
}
