package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/9
 * Time: 18:48
 */
public interface RediseService {
    TaotaoResult syncContent(long contentCid);
}
