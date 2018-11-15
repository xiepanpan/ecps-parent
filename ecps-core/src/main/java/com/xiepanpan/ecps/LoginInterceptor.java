package com.xiepanpan.ecps;

import com.xiepanpan.ecps.model.TsPtlUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * describe: 用户登录拦截器
 *
 * @author xiepanpan
 * @date 2018/11/15
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TsPtlUser user = (TsPtlUser) request.getSession().getAttribute("user");
        if (user==null) {
            String path = request.getContextPath();
            response.sendRedirect(path+"/user/toLogin.do");
            //拦截
            return false;
        }else {
            //放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
