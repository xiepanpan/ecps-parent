package com.xiepanpan.ecps.controller;

import com.xiepanpan.ecps.model.EbShipAddr;
import com.xiepanpan.ecps.model.TsPtlUser;
import com.xiepanpan.ecps.service.EbShipAddrService;
import com.xiepanpan.ecps.service.TsPtlUserService;
import com.xiepanpan.ecps.utils.ECPSUtils;
import com.xiepanpan.ecps.utils.MD5;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * describe:用户登录控制层
 *
 * @author xiepanpan
 * @date 2018/11/12
 */
@Controller
@RequestMapping("/user")
public class EbUserController {


    @Autowired
    private TsPtlUserService tsPtlUserService;
    @Autowired
    private EbShipAddrService ebShipAddrService;

    /**
     *  跳转到登录界面
     * @return
     */
    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "passport/login";
    }

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/getImage.do")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("#######################生成数字和字母的验证码#######################");
        BufferedImage img = new BufferedImage(68, 22,

                BufferedImage.TYPE_INT_RGB);

        // 得到该图片的绘图对象

        Graphics g = img.getGraphics();

        Random r = new Random();

        Color c = new Color(200, 150, 255);

        g.setColor(c);

        // 填充整个图片的颜色

        g.fillRect(0, 0, 68, 22);

        // 向图片中输出数字和字母

        StringBuffer sb = new StringBuffer();

        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        int index, len = ch.length;

        for (int i = 0; i < 4; i++) {

            index = r.nextInt(len);

            g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt

                    (255)));

            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
            // 输出的  字体和大小

            g.drawString("" + ch[index], (i * 15) + 3, 18);
            //写什么数字，在图片 的什么位置画

            sb.append(ch[index]);

        }

        //验证码存储在session中来进行校验
        request.getSession().setAttribute("piccode", sb.toString());

        ImageIO.write(img, "JPG", response.getOutputStream());
    }


    /**
     * 登录验证并跳转
     * @param username
     * @param password
     * @param captcha
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/login.do")
    public String login(String username, String password, String captcha, HttpSession session,Model model) {
        //获得sesion中正确的验证码
        String captchaSession = (String) session.getAttribute("piccode");
        if (!StringUtils.equalsIgnoreCase(captchaSession,captcha)) {
            //写入错误信息
            model.addAttribute("tip","captcha_error");
            return "passport/login";
        }
        password = MD5.GetMD5Code(password);
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        TsPtlUser tsPtlUser = tsPtlUserService.selectUserByUsernameAndPwd(map);
        if(tsPtlUser==null) {
            //在数据库中为查到用户信息
            model.addAttribute("tip","userpwd_error");
            return "passport/login";
        }
        //用户信息写入session
        session.setAttribute("user",tsPtlUser);
        // 重定向到商品首页
        return "redirect:/item/toIndex.do";
    }

    @RequestMapping(value = "/getUser.do")
    @ResponseBody
    public void getUser(HttpSession httpSession,HttpServletResponse response) {
        TsPtlUser user = (TsPtlUser) httpSession.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("user",user);
        ECPSUtils.printAjax(response,jsonObject.toString());
    }

    /**
     * 跳转个人首页
     * @return
     */
    @RequestMapping("/login/toPersonIndex.do")
    public String toPersonIndex(){
        return "person/index";
    }

    /**
     * 展示收货地址
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/login/listAddr.do")
    public String listAddr(HttpSession session,Model model) {
        TsPtlUser user = (TsPtlUser) session.getAttribute("user");
        List<EbShipAddr> ebShipAddrList = ebShipAddrService.selectAddrByUserId(user.getPtlUserId());
        model.addAttribute("ebShipAddrList",ebShipAddrList);
        return "person/deliverAddress";
    }
}
