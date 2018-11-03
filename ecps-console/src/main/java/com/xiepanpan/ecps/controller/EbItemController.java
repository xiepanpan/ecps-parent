package com.xiepanpan.ecps.controller;

import com.xiepanpan.ecps.model.EbBrand;
import com.xiepanpan.ecps.service.EbBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
