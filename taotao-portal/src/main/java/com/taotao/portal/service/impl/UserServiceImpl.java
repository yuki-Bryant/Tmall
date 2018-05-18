package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/15
 * Time: 14:26
 */
@Service
public class UserServiceImpl implements UserService {

    //将基础uRL和登录URL改为public,c传递给拦截器
    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;

    @Value("${SSO_DOMAIN_BASE_URL}")
    public String SSO_DOMAIN_BASE_URL;

    @Value("${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;

    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;

    @Override
    public TbUser getUserByToken(String token) {
        try {
            String json = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN+token);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,TbUser.class);
            if (taotaoResult.getStatus()==200){
                return (TbUser) taotaoResult.getData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
