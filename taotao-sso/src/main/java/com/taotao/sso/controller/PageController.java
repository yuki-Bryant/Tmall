package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面跳转
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/12
 * Time: 10:43
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/register")
    public String showRegister(){
        return "register";
    }

    //接收参数，回调
    @RequestMapping("/login")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }
}
