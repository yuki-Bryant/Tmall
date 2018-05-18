package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/16
 * Time: 14:36
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;

    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    @Override
    public String createOrder(Order order) {
        //调用order服务
        String result = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
        TaotaoResult taotaoResult = TaotaoResult.format(result);
        if (taotaoResult.getStatus()==200){
            Object orderId = taotaoResult.getData();
            return orderId.toString();
        }
        return "";
    }
}
