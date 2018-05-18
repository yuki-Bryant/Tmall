package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 20:39
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${REDIS_SESSION_EXPIRE}")
    private int REDIS_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String content, int type) {
        //创建查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //对数据进行校验,1,2,3分别代表username,phone,email
        //用户名校验
        if (type==1){
            criteria.andUsernameEqualTo(content);
        }else if (type==2){
            criteria.andPhoneEqualTo(content);
        }else if (type==3){
            criteria.andEmailEqualTo(content);
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list==null||list.size()==0){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult userRegister(TbUser tbUser) {
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        //md5加密
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        userMapper.insert(tbUser);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password,
                                  HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        //没有查到
        if (list==null||list.size()==0){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        TbUser user = list.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return TaotaoResult.build(400,"密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //把用户信息写入redis,还要设置过期时间
        String key = REDIS_USER_SESSION_KEY+":"+token;
        //将密码清空
        user.setPassword(null);
        jedisClient.set(key, JsonUtils.objectToJson(user));
        jedisClient.expire(key,REDIS_SESSION_EXPIRE);

        //将token写入cookies,有效期为关闭页面就失效
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        CookieUtils.setCookie(request,response,"TT-TOKEN",token);

        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String key = REDIS_USER_SESSION_KEY+":"+token;
        String userJson = jedisClient.get(key);
        if (StringUtils.isBlank(userJson)){
            return TaotaoResult.build(500,"用户未登录，请登录");
        }
        //更新过期时间
        jedisClient.expire(key,REDIS_SESSION_EXPIRE);
        return TaotaoResult.ok(JsonUtils.jsonToPojo(userJson,TbUser.class));
    }

    @Override
    public TaotaoResult userLogout(String token) {
        String key = REDIS_USER_SESSION_KEY+":"+token;
        //判断是否存在
        String userJson = jedisClient.get(key);
        if (StringUtils.isBlank(userJson)){
            return TaotaoResult.build(500,"用户未登录");
        }
        jedisClient.delete(key);
        return TaotaoResult.ok("");
    }
}
