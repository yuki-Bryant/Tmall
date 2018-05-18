package com.taotao.order.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/16
 * Time: 9:10
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class OrederServiceImpl implements OrderService{

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;

    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;

    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;


    @Override
    public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping) {
        //向订单表中插入记录
        //获得订单号
        String string = jedisClient.get(ORDER_GEN_KEY);//查看是否有值
        if (StringUtils.isBlank(string)){
            jedisClient.set(ORDER_GEN_KEY,ORDER_INIT_ID);
        }
        long orderId = jedisClient.incr(ORDER_GEN_KEY);
        //补全pojo
        Date date = new Date();
        order.setOrderId(orderId+"");
        order.setStatus(1);
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setBuyerRate(0);

        orderMapper.insert(order);
        //插入订单明细
        for (TbOrderItem orderItem:orderItemList){
            //先取订单明细id
            long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            orderItem.setOrderId(orderId+"");
            orderItem.setId(orderDetailId+"");
            orderItemMapper.insert(orderItem);
        }
        //插入物流表
        orderShipping.setOrderId(orderId+"");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingMapper.insert(orderShipping);

        return TaotaoResult.ok(orderId);
    }
}
