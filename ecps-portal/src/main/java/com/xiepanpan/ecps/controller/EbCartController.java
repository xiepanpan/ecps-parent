package com.xiepanpan.ecps.controller;


import com.xiepanpan.ecps.service.EbCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * describe:购物车控制层
 *
 * @author xiepanpan
 * @date 2018/11/12
 */
@Controller
@RequestMapping("/cart")
public class EbCartController {

    @Autowired
    private EbCartService ebCartService;

    /**
     * 添加购物车
     * @param request
     * @param response
     * @param skuId
     * @param quantity
     * @return
     */
    @RequestMapping("/addCart.do")
    public String addCart(HttpServletRequest request,HttpServletResponse response,Long skuId,Integer quantity) {
        ebCartService.addCart(request,response,skuId,quantity);
        return "redirect:listCart.do";
    }

    @RequestMapping("/listCart.do")
    public String listCart(HttpServletRequest request,HttpServletResponse response) {
        return "shop/car";
    }

}
