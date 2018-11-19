package com.xiepanpan.ecps.service;

import com.xiepanpan.ecps.model.EbCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车业务层接口
 */
public interface EbCartService {

    /**
     * 添加购物车
     * @param request
     * @param response
     * @param skuId
     * @param quantity
     */
    public void addCart(HttpServletRequest request, HttpServletResponse response,Long skuId,Integer quantity);

    /**
     * 查询所有的购物车集合
     * @param request
     * @param response
     * @return
     */
    public List<EbCart> selectCartList(HttpServletRequest request,HttpServletResponse response);

    /**
     * 修改购物车
     * @param request
     * @param response
     * @param skuId
     * @param quantity
     */
    public void modifyCart(HttpServletRequest request,HttpServletResponse response,Long skuId,Integer quantity);

    /**
     * 清空购物车
     * @param request
     * @param response
     * @param skuId
     * @param quantity
     */
    public void clearCart(HttpServletRequest request,HttpServletResponse response,Long skuId,Integer quantity);
}
