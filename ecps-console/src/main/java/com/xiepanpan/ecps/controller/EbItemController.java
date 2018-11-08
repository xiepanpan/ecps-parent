package com.xiepanpan.ecps.controller;

import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbBrandService;
import com.xiepanpan.ecps.service.EbFeatureService;
import com.xiepanpan.ecps.service.EbItemService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
     * @param model
     * @return
     */
    @RequestMapping("/listBrand.do")
    public String listBrand(Model model) {
        List<EbBrand> ebBrandList = ebBrandService.selectBrandAll();
        model.addAttribute("ebBrandList",ebBrandList);
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
        if (ebList.size()>0) {
            jsonObject.accumulate("flag","no");
        }else {
            jsonObject.accumulate("flag","yes");
        }
        return jsonObject.toString();
    }

    /**
     * 品牌添加  前台使用遮罩防止二次提交
     * @param ebBrand
     * @return
     */
    @RequestMapping("/addBrand.do")
    public String addBrand(EbBrand ebBrand){
        ebBrandService.saveBrand(ebBrand);
        return "redirect:listBrand.do";
    }

    /**
     * 展示所有商品
     * @return
     */
    @RequestMapping("/listItem.do")
    public String listItem(QueryCondition queryCondition,Model model) {
        List<EbBrand> ebBrandList = ebBrandService.selectBrandAll();
        model.addAttribute("ebBrandList",ebBrandList);
        if (queryCondition.getPageNo()==null) {
            queryCondition.setPageNo(1);
        }
        Page page = ebItemService.selectItemByCondition(queryCondition);
        model.addAttribute("page",page);
        model.addAttribute("qc",queryCondition);
        return "item/list";
    }

    /**
     * 跳转商品添加
     * @param model
     * @return
     */
    @RequestMapping("/toAddItem.do")
    public String toAddItem(Model model){
        //查询品牌
        List<EbBrand> ebBrandList = ebBrandService.selectBrandAll();
        model.addAttribute("bList",ebBrandList);
        //查询普通属性
        List<EbFeature> commList = ebFeatureService.selectCommFeature();
        model.addAttribute("commList",commList);
        List<EbFeature> specList = ebFeatureService.selectSpecFeature();
        model.addAttribute("specList",specList);
        return "item/addItem";
    }

    @RequestMapping("addItem.do")
    public String addItem(EbItem ebItem, EbItemClob ebItemClob, HttpServletRequest request) {
        ebItem.setItemNo(new SimpleDateFormat("yyyMMddHHmmssSSS").format(new Date()));
        //从后台查询普通属性的集合 是tab_3中所展示的属性
        List<EbFeature> commList = ebFeatureService.selectCommFeature();
        for (EbFeature ebFeature:commList) {
            //获得属性id 对应tab3文本域中的name
            Long featureId = ebFeature.getFeatureId();
            //复选框取值
            if (ebFeature.getInputType()==3) {
                //数组转字符串
                String[] paraVals = request.getParameterValues(featureId + "");
                if (paraVals!=null && paraVals.length>0) {
                    String paraValues = "";
                    for (String paraValue:paraVals) {
                        paraValues=paraValues+paraValue+",";
                    }
                    paraValues=paraValues.substring(0,paraValues.length()-1);
                    EbParaValue ebParaValue = new EbParaValue();
                    ebParaValue.setParaValue(paraValues);
                    ebParaValue.setFeatureId(featureId);
                }
            }

        }
        return "redirect:listItem.do?showStatus=1";
    }

}
