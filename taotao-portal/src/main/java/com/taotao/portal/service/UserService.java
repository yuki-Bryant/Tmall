package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/15
 * Time: 14:26
 */
public interface UserService {
    TbUser getUserByToken(String token);
}
