package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 20:38
 */
public interface UserService {
    TaotaoResult checkData(String content,int type);
    TaotaoResult userRegister(TbUser tbUser);
    TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);
    TaotaoResult getUserByToken(String token);
    TaotaoResult userLogout(String token);
}
