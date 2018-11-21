package com.xiepanpan.ecps.service.impl;

import com.xiepanpan.ecps.model.EbCart;
import com.xiepanpan.ecps.model.EbSku;
import com.xiepanpan.ecps.model.EbSpecValue;
import com.xiepanpan.ecps.service.EbCartService;
import com.xiepanpan.ecps.service.EbSkuService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ecps-parent
 * @description: 购物车业务层
 * @author: xiepanpan
 * @create: 2018-11-19 21:05
 **/
@Service
public class EbCartServiceImpl implements EbCartService{

    @Autowired
    private EbSkuService ebSkuService;

    @Override
    public void addCart(HttpServletRequest request, HttpServletResponse response, Long skuId, Integer quantity) {
        Cookie[] cookies = request.getCookies();
        List<EbCart> ebCartList= new ArrayList<EbCart>();
        //json数组转java对象
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(EbCart.class);
        //排除属性 不不需要转
        jsonConfig.setExcludes(new String[]{"ebSku"});
        if (cookies!=null&&cookies.length>0) {
            for (Cookie cookie:cookies) {
                String cookieName = cookie.getName();
                if (StringUtils.equals(cookieName, ECPSUtils.readProp("cart_key"))) {
                    //获得购物车cookie中对应的值
                    String cookieValue = cookie.getValue();
                    //解码
                    cookieValue = URLDecoder.decode(cookieValue);
                    //字符串转json数组
                    JSONArray jsonArray = JSONArray.fromObject(cookieValue);

                    ebCartList = (List<EbCart>) JSONSerializer.toJava(jsonArray, jsonConfig);
                    //cookie中是否存在选中的商品的标志
                    boolean isExist=false;
                    for (EbCart ebCart:ebCartList) {
                        if (ebCart.getSkuId().longValue()==skuId.longValue()){
                            //如果cookie中有选中的商品 则数量相加
                            ebCart.setQuantity(ebCart.getQuantity()+quantity);
                            isExist=true;
                            break;
                        }
                    }
                    if (!isExist) {
                        //cookie中不存在选中商品 则进行创建
                        EbCart ebCart = new EbCart();
                        ebCart.setSkuId(skuId);
                        ebCart.setQuantity(quantity);
                        ebCartList.add(ebCart);
                    }
                }else {
                    //cookie中没有购物车的key 说明购物车为空 创建购物车信息 添加到cookie中
                    EbCart ebCart = new EbCart();
                    ebCart.setSkuId(skuId);
                    ebCart.setQuantity(quantity);
                    ebCartList.add(ebCart);
                }
            }
        }
        //写入cookie中
        JSONArray jsonArray = JSONArray.fromObject(ebCartList, jsonConfig);
        String result = jsonArray.toString();
        //编码
        result = URLEncoder.encode(result);
        Cookie cookie = new Cookie("cookie_cart_key",result);
        //路径 根目录
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    @Override
    public void removeCart(HttpServletRequest request, HttpServletResponse response, Long skuId) {
        Cookie[] cookies = request.getCookies();
        List<EbCart> ebCartList= new ArrayList<EbCart>();
        //json数组转java对象
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(EbCart.class);
        //排除属性 不不需要转
        jsonConfig.setExcludes(new String[]{"ebSku"});
        if (cookies!=null&&cookies.length>0) {
            for (Cookie cookie:cookies) {
                String cookieName = cookie.getName();
                if (StringUtils.equals(cookieName, ECPSUtils.readProp("cart_key"))) {
                    //获得购物车cookie中对应的值
                    String cookieValue = cookie.getValue();
                    //解码
                    cookieValue = URLDecoder.decode(cookieValue);
                    //字符串转json数组
                    JSONArray jsonArray = JSONArray.fromObject(cookieValue);

                    ebCartList = (List<EbCart>) JSONSerializer.toJava(jsonArray, jsonConfig);

                    for (int i = 0; i <ebCartList.size() ; i++) {
                        EbCart ebCart = ebCartList.get(i);
                        if (ebCart.getSkuId().longValue()==skuId.longValue()) {
                            ebCartList.remove(ebCart);
                        }
                    }
                }
            }
        }
        //写入cookie中
        JSONArray jsonArray = JSONArray.fromObject(ebCartList, jsonConfig);
        String result = jsonArray.toString();
        //编码
        result = URLEncoder.encode(result);
        Cookie cookie = new Cookie("cookie_cart_key",result);
        //路径 根目录
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    @Override
    public List<EbCart> selectCartList(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        List<EbCart> ebCartList= new ArrayList<EbCart>();
        //json数组转java对象
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(EbCart.class);
        //排除属性 不不需要转
        jsonConfig.setExcludes(new String[]{"ebSku"});
        if (cookies!=null&&cookies.length>0) {
            for (Cookie cookie:cookies) {
                String cookieName = cookie.getName();
                if (StringUtils.equals(cookieName, ECPSUtils.readProp("cart_key"))) {
                    //获得购物车cookie中对应的值
                    String cookieValue = cookie.getValue();
                    //解码
                    cookieValue = URLDecoder.decode(cookieValue);
                    //字符串转json数组
                    JSONArray jsonArray = JSONArray.fromObject(cookieValue);

                    ebCartList = (List<EbCart>) JSONSerializer.toJava(jsonArray, jsonConfig);
                    for (EbCart ebCart:ebCartList) {
                        //从redis中取最小销售单元的详细信息
                        EbSku ebSku = ebSkuService.getSkuDetailByIdFromRedis(ebCart.getSkuId());
                        ebCart.setEbSku(ebSku);
                    }
                }
            }
        }
        return ebCartList;
    }

    @Override
    public void modifyCart(HttpServletRequest request, HttpServletResponse response, Long skuId, Integer modifyQuantity) {
        Cookie[] cookies = request.getCookies();
        List<EbCart> ebCartList= new ArrayList<EbCart>();
        //json数组转java对象
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(EbCart.class);
        //排除属性 不不需要转
        jsonConfig.setExcludes(new String[]{"ebSku"});
        if (cookies!=null&&cookies.length>0) {
            for (Cookie cookie:cookies) {
                String cookieName = cookie.getName();
                if (StringUtils.equals(cookieName, ECPSUtils.readProp("cart_key"))) {
                    //获得购物车cookie中对应的值
                    String cookieValue = cookie.getValue();
                    //解码
                    cookieValue = URLDecoder.decode(cookieValue);
                    //字符串转json数组
                    JSONArray jsonArray = JSONArray.fromObject(cookieValue);

                    ebCartList = (List<EbCart>) JSONSerializer.toJava(jsonArray, jsonConfig);
                    for (EbCart ebCart:ebCartList) {
                        if (ebCart.getSkuId().longValue()==skuId.longValue()) {
                           ebCart.setQuantity(modifyQuantity);
                        }
                    }
                }
            }
        }
        //写入cookie中
        JSONArray jsonArray = JSONArray.fromObject(ebCartList, jsonConfig);
        String result = jsonArray.toString();
        //编码
        result = URLEncoder.encode(result);
        Cookie cookie = new Cookie("cookie_cart_key",result);
        //路径 根目录
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    @Override
    public void clearCart(HttpServletRequest request, HttpServletResponse response, Long skuId, Integer quantity) {

    }

    @Override
    public String validCart(HttpServletRequest request, HttpServletResponse response) {
        String result = "success";
        Cookie[] cookies = request.getCookies();
        List<EbCart> ebCartList= new ArrayList<EbCart>();
        //json数组转java对象
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(EbCart.class);
        //排除属性 不不需要转
        jsonConfig.setExcludes(new String[]{"ebSku"});
        if (cookies!=null&&cookies.length>0) {
            for (Cookie cookie:cookies) {
                String cookieName = cookie.getName();
                if (StringUtils.equals(cookieName, ECPSUtils.readProp("cart_key"))) {
                    //获得购物车cookie中对应的值
                    String cookieValue = cookie.getValue();
                    //解码
                    cookieValue = URLDecoder.decode(cookieValue);
                    //字符串转json数组
                    JSONArray jsonArray = JSONArray.fromObject(cookieValue);

                    ebCartList = (List<EbCart>) JSONSerializer.toJava(jsonArray, jsonConfig);
                    for (EbCart ebCart:ebCartList) {
                        EbSku ebSku = ebSkuService.getSkuDetailByIdFromRedis(ebCart.getSkuId());
                        if (ebSku.getStockInventory()<ebCart.getQuantity()) {
                            result= ebSku.getEbItem().getItemName();
                            for (EbSpecValue ebSpecValue:ebSku.getEbSpecValueList()) {
                                result=result+ebSpecValue.getSpecValue();
                            }
                            result=result+"库存不足"+ebCart.getQuantity()+"个,实际库存是"+ebSku.getStockInventory();
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
}
