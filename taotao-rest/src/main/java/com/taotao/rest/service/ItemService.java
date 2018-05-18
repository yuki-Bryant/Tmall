package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 11:11
 */
public interface ItemService {
    TaotaoResult getItemBaseInfo(long itemId);
    TaotaoResult getItemDesc(long itemId);
    TaotaoResult getItemParams(long itemId);
}
