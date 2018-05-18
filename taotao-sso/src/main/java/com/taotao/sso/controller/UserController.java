package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 21:20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkUserData(@PathVariable String param,@PathVariable Integer type,
                                      String callback){
        TaotaoResult result = null;
        //先校验参数
        if (StringUtils.isBlank(param)){
            result =  TaotaoResult.build(400,"校验内容不能为空");
        }
        if (type==null){
            result = TaotaoResult.build(400,"校验类型不能为空");
        }
        if (type!=1&&type!=2&&type!=3){
            result = TaotaoResult.build(400,"校验类型错误");
        }
        //校验出错
        if (result!=null){
            if (callback!=null){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }else{
                return result;
            }
        }

        //调用服务
        result = userService.checkData(param,type);
        if (null != callback) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        } else {
            return result;
        }
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userRegister(TbUser user){
        try {
            TaotaoResult result = userService.userRegister(user);
            return result;
        }catch (Exception e){
            return TaotaoResult.build(500,"注册失败");
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password,
                                  HttpServletRequest request, HttpServletResponse response){
        try {
            TaotaoResult result = userService.userLogin(username,password,request,response);
            return result;
        }catch (Exception e){
            return TaotaoResult.build(500,"登录失败");
        }
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(token);
        }catch (Exception e){
            return TaotaoResult.build(500,"未登录");
        }

        //判断是否有回调方法
        if (!StringUtils.isBlank(callback)){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }

    @RequestMapping("/logout/{token}")
    public String userLogout(@PathVariable String token){
        TaotaoResult result = userService.userLogout(token);
        return "redirect:/page/login.html";
    }
}
