package com.xiepanpan.ecps.controller;


import com.xiepanpan.ecps.model.EbCart;
import com.xiepanpan.ecps.model.EbOrder;
import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.TsPtlUser;
import com.xiepanpan.ecps.service.EbCartService;
import com.xiepanpan.ecps.service.EbShipAddrService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private EbShipAddrService ebShipAddrService;

    /**
     * 跳转到订单页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toSubmitOrder.do")
    public String addCart(HttpSession session,HttpServletRequest request, HttpServletResponse response,Model model) {
        TsPtlUser user = (TsPtlUser) session.getAttribute("user");
        //收货地址查询
        List<EbShipAddr> ebShipAddrList = ebShipAddrService.selectAddrByUserIdFromRedis(user.getPtlUserId());
        model.addAttribute("ebShipAddrList",ebShipAddrList);

        //购物车数据查询
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
        return "shop/confirmProductCase";
    }

    /**
     * 提交订单
     * @param ebOrder
     * @param request
     * @param response
     * @param model
     * @param session
     * @param address
     * @return
     */
    @RequestMapping("/submitOrder.do")
    public String submitOrder(EbOrder ebOrder,HttpServletRequest request,HttpServletResponse response,
                              Model model,HttpSession session,String address){
        TsPtlUser user = (TsPtlUser) session.getAttribute("user");
        ebOrder.setPtlUserId(user.getPtlUserId());
        ebOrder.setUsername(user.getUsername());
        ebOrder.setOrderNum(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        if (!StringUtils.equals(address,"add")) {
            //根据用户id和地址id从redis中取一条地址信息
            EbShipAddr ebShipAddr = ebShipAddrService.selectAddrByIdFromRedis(user.getPtlUserId(), new Long(address));
            BeanUtils.copyProperties(ebOrder,ebShipAddr);
        }

        //
        return null;
    }



}
