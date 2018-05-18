package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 15:27
 */
public interface ItemService {
    ItemInfo getItemById(long itemId);
    String getItemDesc(long itemId);
    //返回一个html片段
    String getItemParams(long itemId);
}
