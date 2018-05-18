package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车controller
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/15
 * Time: 15:53
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    private String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer count,
                               HttpServletRequest request,HttpServletResponse response){
        TaotaoResult result = cartService.addCartItem(request,response,itemId,count);
        return "redirect:/cart/success.html";//使用根路径
    }

    //防止刷新多次添加购物车
    @RequestMapping("/success")
    public String showSuccess(){
        return "cart-success";
    }

    @RequestMapping("/cart")
    private String showCart(HttpServletRequest request,HttpServletResponse response,Model model){
        List<CartItem> list = cartService.getCartItemList(request,response);
        model.addAttribute("cartList",list);
        return "cart";
    }

    /**
     * 更新购物车商品数量
     */
    @RequestMapping("/update/count/{itemId}/{count}")
    @ResponseBody
    private TaotaoResult updateNum(@PathVariable Long itemId, @PathVariable Integer count,
                                  HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = cartService.updateCartItem(request,response,itemId,count);
        // 返回结果
        return result;
    }

    /**
     * 删除购物车商品
     */
    @RequestMapping("/delete/{itemId}")
    private String deleteCart(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response){
        TaotaoResult result = cartService.deleteCartItem(request,response,itemId);
        return "redirect:/cart/cart.html";
    }
}
