package com.xiepanpan.ecps.controller;


import com.xiepanpan.ecps.exception.EbStockException;
import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbCartService;
import com.xiepanpan.ecps.service.EbOrderService;
import com.xiepanpan.ecps.service.EbShipAddrService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private EbOrderService ebOrderService;

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
                              Model model,HttpSession session,String address) throws Exception {
        TsPtlUser user = (TsPtlUser) session.getAttribute("user");
        ebOrder.setUsername(user.getUsername());
        ebOrder.setOrderNum(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        if (!StringUtils.equals(address,"add")) {
            //根据用户id和地址id从redis中取一条地址信息
            EbShipAddr ebShipAddr = ebShipAddrService.selectAddrByIdFromRedis(user.getPtlUserId(), new Long(address));
            BeanUtils.copyProperties(ebOrder,ebShipAddr);
        }

        ebOrder.setPtlUserId(user.getPtlUserId());
        //查询购物车的数据来创建订单的明细
        List<EbCart> cartList = ebCartService.selectCartList(request, response);
        List<EbOrderDetail> detailList = new ArrayList<EbOrderDetail>();
        for(EbCart ebCart : cartList){
            EbOrderDetail detail = new EbOrderDetail();
            detail.setItemId(ebCart.getEbSku().getEbItem().getItemId());
            detail.setItemName(ebCart.getEbSku().getEbItem().getItemName());
            detail.setItemNo(ebCart.getEbSku().getEbItem().getItemNo());
            detail.setSkuId(ebCart.getSkuId());
            String specVals = "";
            for(EbSpecValue spc : ebCart.getEbSku().getEbSpecValueList()){
                specVals = specVals + spc.getSpecValue()+",";
            }
            specVals = specVals.substring(0, specVals.length() - 1);
            detail.setSkuSpec(specVals);
            detail.setMarketPrice(ebCart.getEbSku().getMarketPrice());
            detail.setSkuPrice(ebCart.getEbSku().getSkuPrice());
            detail.setQuantity(ebCart.getQuantity());
            detailList.add(detail);
        }
        try {
            String processInstanceId = ebOrderService.saveOrder(ebOrder, detailList, request, response);
            session.setAttribute("processInstanceId",processInstanceId);
            session.setAttribute("orderId",ebOrder.getOrderId());
            model.addAttribute("ebOrder",ebOrder);
        } catch (Exception e) {
            if (e instanceof EbStockException) {
                model.addAttribute("tip","stock_error");
            }
            e.printStackTrace();

        }
        return "shop/confirmProductCase2";
    }

    /**
     * 支付订单
     * @param session
     * @param printWriter
     */
    @RequestMapping("/payOrder.do")
    public void payOrder(HttpSession session, PrintWriter printWriter) {
        String processInstanceId = (String) session.getAttribute("processInstanceId");
        Long orderId = (Long) session.getAttribute("orderId");
        ebOrderService.updatePayOrder(processInstanceId,orderId);
        printWriter.write("success");
    }


}
