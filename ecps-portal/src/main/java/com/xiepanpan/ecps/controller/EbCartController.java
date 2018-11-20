package com.xiepanpan.ecps.controller;


import com.xiepanpan.ecps.model.EbCart;
import com.xiepanpan.ecps.service.EbCartService;
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

    /**
     * 查询购物车列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/listCart.do")
    public String listCart(HttpServletRequest request,HttpServletResponse response,Model model) {
        List<EbCart> ebCartList = ebCartService.selectCartList(request, response);
        //总共的商品数
        Integer ebItemNum = 0;
        BigDecimal totalPrice=new BigDecimal(0);
        for (EbCart ebCart:ebCartList) {
            ebItemNum=ebItemNum+ebCart.getQuantity();
            totalPrice=totalPrice.add(ebCart.getEbSku().getSkuPrice().multiply(new BigDecimal(ebCart.getQuantity())));
        }
        model.addAttribute("ebCartList",ebCartList);
        model.addAttribute("ebItemNum",ebItemNum);
        model.addAttribute("totalPrice",totalPrice);
        return "shop/car";
    }

}
