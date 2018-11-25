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
     * 移除购物车
     * @param request
     * @param response
     * @param skuId
     */
    public void removeCart(HttpServletRequest request,HttpServletResponse response,Long skuId);

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
     * @param modifyQuantity 购物车修改的数量
     */
    public void modifyCart(HttpServletRequest request,HttpServletResponse response,Long skuId,Integer modifyQuantity);

    /**
     * 清空购物车
     * @param request
     * @param response
     * @param skuId
     * @param quantity
     */
    public void clearCart(HttpServletRequest request,HttpServletResponse response);

    /**
     * 结算前校验购物车所有商品库存
     * @param request
     * @param response
     * @return
     */
    public String validCart(HttpServletRequest request,HttpServletResponse response);
}
