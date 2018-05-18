package com.taotao.service;

import com.taotao.common.pojo.EuDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 9:23
 */
public interface ItemService {
    TbItem getItemById(long itemId);
    EuDataGridResult getItemList(int page,int rows);
    TaotaoResult createItem(TbItem item);
    //将商品描述一起添加到数据库表中
    TaotaoResult addItem(TbItem item, String itemDesc, String itemParams);
}
