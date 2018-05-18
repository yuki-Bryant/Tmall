package com.taotao.portal.intercerptor;

import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/15
 * Time: 14:17
 */
public class LoginIntercerptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //在handler处理之前，返回值决定handler是否执行
        /**
         * 判断用户是否登录
         * 1.从cookie取用户信息
         * 2.取不到用户信息，跳转到登录界面，把用户请求的url作为参数传递给登录页面
         * 3.取到了用户信息，放行。
         */
        String token = CookieUtils.getCookieValue(httpServletRequest,"TT-TOKEN");
        //编写一个service，用token换取用户信息
        TbUser user = userService.getUserByToken(token);
        if (user==null){
            //跳转到登录页面
            httpServletResponse.sendRedirect(userService.SSO_DOMAIN_BASE_URL+userService.SSO_PAGE_LOGIN+
            "?redirect=" +httpServletRequest.getRequestURL());
            return false;
        }
        httpServletRequest.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //在handler处理之后，modelAndView返回之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //modelAndView返回之后
    }
}
