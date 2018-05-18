package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/15
 * Time: 15:15
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;


    @Override
    public TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response,
                                    long itemId, int count) {
        CartItem cartItem = null;
        //先取购物车商品列表
        List<CartItem> list = getCartList(request);
        //判断是否有该商品
        for (CartItem citem:list) {
            if (citem.getId()==itemId){
                citem.setCount(citem.getCount()+count);
                cartItem = citem;
                break;
            }
        }
        if (cartItem==null){
            cartItem = new CartItem();
            //取商品信息
            String url = REST_BASE_URL+ITEM_INFO_URL+itemId;
            try {
                String result = HttpClientUtil.doGet(url);
                if (!StringUtils.isBlank(result)){
                    TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, TbItem.class);
                    if (taotaoResult.getStatus()==200){
                        TbItem item = (TbItem) taotaoResult.getData();
                        cartItem.setId(itemId);
                        cartItem.setPrice(item.getPrice());
                        cartItem.setTitle(item.getTitle());
                        //取一张图片
                        cartItem.setImage(item.getImage()==null? "":item.getImage().split(",")[0]);
                        cartItem.setCount(count);
                    }
                    //添加到购物车列表
                    list.add(cartItem);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //写回cookie
        CookieUtils.setCookie(request,response,"TT-CART",JsonUtils.objectToJson(list),true);
        return TaotaoResult.ok();
    }

    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        return getCartList(request);
    }

    @Override
    public TaotaoResult updateCartItem(HttpServletRequest request, HttpServletResponse response, long itemId, int count) {
        //先取购物车商品列表
        List<CartItem> list = getCartList(request);
        //根据id获取商品
        for (CartItem citem:list) {
            if (citem.getId()==itemId){
                citem.setCount(count);
                break;
            }
        }
        //写回cookie
        CookieUtils.setCookie(request,response,"TT-CART",JsonUtils.objectToJson(list),true);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCartItem(HttpServletRequest request, HttpServletResponse response, long itemId) {
        List<CartItem> list = getCartList(request);
        //根据id获取商品
        for (CartItem citem:list) {
            if (citem.getId()==itemId){
                list.remove(citem);
                break;
            }
        }
        //写回cookie
        CookieUtils.setCookie(request,response,"TT-CART",JsonUtils.objectToJson(list),true);
        return TaotaoResult.ok();
    }

    //从cookie中取购物车商品列表
    public List<CartItem> getCartList(HttpServletRequest request){
        List<CartItem> list = new ArrayList<>();
        String json = CookieUtils.getCookieValue(request,"TT-CART",true);
        if (!StringUtils.isBlank(json)){
            list = JsonUtils.jsonToList(json,CartItem.class);
        }
        return list;
    }
}
