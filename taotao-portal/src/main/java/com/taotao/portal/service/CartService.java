package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/15
 * Time: 15:14
 */
public interface CartService {
    TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response, long itemId, int count);
    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
    TaotaoResult updateCartItem(HttpServletRequest request, HttpServletResponse response, long itemId, int count);
    TaotaoResult deleteCartItem(HttpServletRequest request, HttpServletResponse response, long itemId);
}
