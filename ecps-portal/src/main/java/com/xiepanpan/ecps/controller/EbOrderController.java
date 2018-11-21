package com.xiepanpan.ecps.controller;


import com.xiepanpan.ecps.model.EbCart;
import com.xiepanpan.ecps.service.EbCartService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * describe:购物车控制层
 *
 * @author xiepanpan
 * @date 2018/11/12
 */
@Controller
@RequestMapping("/order")
public class EbOrderController {

    @Autowired
    private EbCartService ebCartService;

    /**
     * 跳转到订单页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toSubmitOrder.do")
    public String addCart(HttpServletRequest request,HttpServletResponse response) {
        return "shop/confirmProductCase";
    }



}
