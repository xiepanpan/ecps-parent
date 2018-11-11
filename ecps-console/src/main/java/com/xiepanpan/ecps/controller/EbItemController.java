package com.xiepanpan.ecps.controller;

import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbBrandService;
import com.xiepanpan.ecps.service.EbFeatureService;
import com.xiepanpan.ecps.service.EbItemService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * describe:商品控制层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Controller
@RequestMapping("/item")
public class EbItemController {

    @Autowired
    EbBrandService ebBrandService;
    @Autowired
    EbItemService ebItemService;
    @Autowired
    EbFeatureService ebFeatureService;

    @RequestMapping("/toIndex.do")
    public String toIndex() {
        return "item/index";
    }

    /**
     * 展示所有品牌
     *
     * @param model
     * @return
     */
    @RequestMapping("/listBrand.do")
    public String listBrand(Model model) {
        List<EbBrand> ebBrandList = ebBrandService.selectBrandAll();
        model.addAttribute("ebBrandList", ebBrandList);
        return "item/listbrand";
    }

    @RequestMapping("/toAddBrand.do")
    public String toAddBrand(Model model) {
        return "item/addbrand";
    }

    @RequestMapping("/validBrandName.do")
    @ResponseBody
    public String validBrandName(String brandName) {
        List<EbBrand> ebList = ebBrandService.selectBrandByName(brandName);
        JSONObject jsonObject = new JSONObject();
        if (ebList.size() > 0) {
            jsonObject.accumulate("flag", "no");
        } else {
            jsonObject.accumulate("flag", "yes");
        }
        return jsonObject.toString();
    }

    /**
     * 品牌添加  前台使用遮罩防止二次提交
     *
     * @param ebBrand
     * @return
     */
    @RequestMapping("/addBrand.do")
    public String addBrand(EbBrand ebBrand) {
        ebBrandService.saveBrand(ebBrand);
        return "redirect:listBrand.do";
    }

    /**
     * 展示所有商品
     *
     * @return
     */
    @RequestMapping("/listItem.do")
    public String listItem(QueryCondition queryCondition, Model model) {
        List<EbBrand> ebBrandList = ebBrandService.selectBrandAll();
        model.addAttribute("ebBrandList", ebBrandList);
        if (queryCondition.getPageNo() == null) {
            queryCondition.setPageNo(1);
        }
        Page page = ebItemService.selectItemByCondition(queryCondition);
        model.addAttribute("page", page);
        model.addAttribute("qc", queryCondition);
        return "item/list";
    }

    /**
     * 跳转商品添加
     *
     * @param model
     * @return
     */
    @RequestMapping("/toAddItem.do")
    public String toAddItem(Model model) {
        //查询品牌
        List<EbBrand> ebBrandList = ebBrandService.selectBrandAll();
        model.addAttribute("bList", ebBrandList);
        //查询普通属性
        List<EbFeature> commList = ebFeatureService.selectCommFeature();
        model.addAttribute("commList", commList);
        List<EbFeature> specList = ebFeatureService.selectSpecFeature();
        model.addAttribute("specList", specList);
        return "item/addItem";
    }

    @RequestMapping("addItem.do")
    public String addItem(EbItem ebItem, EbItemClob ebItemClob, Integer divNum, HttpServletRequest request) {
        ebItem.setItemNo(new SimpleDateFormat("yyyMMddHHmmssSSS").format(new Date()));
        List<EbParaValue> ebParaValueList = new ArrayList<>();

        //从后台查询普通属性的集合 是tab_3中所展示的属性
        List<EbFeature> commList = ebFeatureService.selectCommFeature();
        for (EbFeature ebFeature : commList) {
            //获得属性id 对应tab3文本域中的name
            Long featureId = ebFeature.getFeatureId();
            //复选框取值
            if (ebFeature.getInputType() == 3) {
                //数组转字符串
                String[] paraVals = request.getParameterValues(featureId + "");
                if (paraVals != null && paraVals.length > 0) {
                    String paraValues = "";
                    for (String paraValue : paraVals) {
                        paraValues = paraValues + paraValue + ",";
                    }
                    paraValues = paraValues.substring(0, paraValues.length() - 1);
                    EbParaValue ebParaValue = new EbParaValue();
                    ebParaValue.setParaValue(paraValues);
                    ebParaValue.setFeatureId(featureId);
                    ebParaValueList.add(ebParaValue);
                }
            } else {
                //获得单选和下拉的值
                String paraVal = request.getParameter(featureId + "");
                if (StringUtils.isNotBlank(paraVal)) {
                    //创建商品参数值的表的对象
                    EbParaValue ebParaValue = new EbParaValue();
                    ebParaValue.setFeatureId(featureId);
                    ebParaValue.setParaValue(paraVal);
                    ebParaValueList.add(ebParaValue);
                }
            }

        }

        //tab4
        List<EbFeature> specList = ebFeatureService.selectSpecFeature();

        //存放最小是销售单元
        List<EbSku> ebSkuList = new ArrayList<>();
        for (int i = 1; i <= divNum; i++) {
            //商城价 必填项
            String skuPrice = request.getParameter("skuPrice" + i);
            //库存 必填项
            String stockInventory = request.getParameter("stockInventory" + i);
            //根据必填项来判断是否断档 为空显然断档了
            if (StringUtils.isNotBlank(skuPrice) && StringUtils.isNotBlank(stockInventory)) {
                //sort1 skuPrice1 marketPrice1 stockInventory1 skuUpperLimit1 sku1 location1 showStatus1 skuType
                String sort = request.getParameter("sort" + i);
                String marketPrice = request.getParameter("marketPrice" + i);
                String skuUpperLimit = request.getParameter("skuUpperLimit" + i);
                String sku = request.getParameter("sku" + i);
                String location = request.getParameter("location" + i);
                String showStatus = request.getParameter("showStatus" + i);
                String skuType = request.getParameter("skuType" + i);
                EbSku ebSku = new EbSku();
                //设置参数
                //BigDecimal 使用大字段类型 在计算精度时不失精度
                ebSku.setSkuPrice(new BigDecimal(skuPrice));
                ebSku.setStockInventory(new Integer(stockInventory));
                if (StringUtils.isNotBlank(sort)) {
                    ebSku.setSkuSort(new Integer(sort));
                }
                if (StringUtils.isNotBlank(marketPrice)) {
                    ebSku.setMarketPrice(new BigDecimal(marketPrice));
                }
                if (StringUtils.isNotBlank(skuUpperLimit)) {
                    ebSku.setSkuUpperLimit(new Integer(skuUpperLimit));
                }
                ebSku.setSku(sku);
                ebSku.setLocation(location);
                if (StringUtils.isNotBlank(showStatus)) {
                    ebSku.setShowStatus(new Short(showStatus));
                }
                if (StringUtils.isNotBlank(skuType)) {
                    ebSku.setSkuType(new Short(skuType));
                }
                //遍历特殊属性
                List<EbSpecValue> ebSpecValueList = new ArrayList<>();
                for (EbFeature ebFeature : specList) {
                    Long featureId = ebFeature.getFeatureId();
                    String specVal = request.getParameter(featureId + "specradio" + i);
                    EbSpecValue ebSpecValue = new EbSpecValue();
                    ebSpecValue.setFeatureId(featureId);
                    ebSpecValue.setSpecValue(specVal);
                    ebSpecValueList.add(ebSpecValue);
                }
                ebSku.setEbSpecValueList(ebSpecValueList);
                ebSkuList.add(ebSku);
            }
        }
        ebItemService.saveItem(ebItem,ebItemClob,ebParaValueList,ebSkuList);

        return "redirect:listItem.do?showStatus=1";
    }

    /**
     * 展示所有审核的商品
     * @param queryCondition
     * @param model
     * @return
     */
    @RequestMapping("/listAuditItem.do")
    public String listAuditItem(QueryCondition queryCondition, Model model) {
        List<EbBrand> ebBrandList = ebBrandService.selectBrandAll();
        model.addAttribute("ebBrandList", ebBrandList);
        if (queryCondition.getPageNo() == null) {
            queryCondition.setPageNo(1);
        }
        Page page = ebItemService.selectItemByCondition(queryCondition);
        model.addAttribute("page", page);
        model.addAttribute("qc", queryCondition);
        return "item/listAudit";
    }
}
