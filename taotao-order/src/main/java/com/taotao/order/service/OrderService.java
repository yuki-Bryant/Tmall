package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/16
 * Time: 9:08
 */
public interface OrderService {
    TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping);
}
