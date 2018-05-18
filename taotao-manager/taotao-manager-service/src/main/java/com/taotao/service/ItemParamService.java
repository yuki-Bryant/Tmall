package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/7
 * Time: 9:30
 */
public interface ItemParamService {
    TaotaoResult checkParam(long cid);
    TaotaoResult addItemParam(long cid, String template);
    TaotaoResult getItemParemByCid(long cid);
}
