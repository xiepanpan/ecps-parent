package com.xiepanpan.ecps.controller;

import com.xiepanpan.ecps.model.*;
import com.xiepanpan.ecps.service.EbBrandService;
import com.xiepanpan.ecps.service.EbFeatureService;
import com.xiepanpan.ecps.service.EbItemService;
import com.xiepanpan.ecps.service.EbOrderService;
import com.xiepanpan.ecps.utils.ECPSUtils;
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
 * describe:订单控制层
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Controller
@RequestMapping("/orderManage")
public class EbOrderController {

    @Autowired
    EbOrderService ebOrderService;

    /**
     * 跳转到订单管理
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex() {
        return "order/index";
    }

    /**
     * 跳转到待付订单首页
     * @return
     */
    @RequestMapping("/listPayOrder.do")
    public String listPayOrder(String assignee,Short isCall,Model model) {
        List<TaskBean> taskBeanList = ebOrderService.selectTaskBeanByAssigneeAndIsCall(assignee, isCall);
        model.addAttribute("taskBeanList",taskBeanList);
        model.addAttribute("isCall",isCall);
        return "order/orderPay/orderPay";
    }

}
