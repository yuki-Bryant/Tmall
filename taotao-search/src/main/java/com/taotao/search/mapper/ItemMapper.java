package com.taotao.search.mapper;


import com.taotao.common.pojo.Item;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 16:37
 */
public interface ItemMapper {
    List<Item> getItemList();
    Item getItem(long id);
}
