package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import com.taotao.portal.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/16
 * Time: 11:09
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model){
        //取出购物车商品列表信息
        List<CartItem> list = cartService.getCartItemList(request,response);
        model.addAttribute("cartList",list);
        return "order-cart";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createOreder(HttpServletRequest request,Order order,Model model){
        //从request中取用户信息,要在拦截器处将用户信息放入request中
       //TbUser user = getUserFromCookie(request);//要多调用服务，直接使用拦截器request
        TbUser user = (TbUser) request.getAttribute("user");
        //把用户信息补充到order对象中
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        //提交订单
        TaotaoResult result = null;
        try {
            String orderId = orderService.createOrder(order);
            //订单创建成功
            model.addAttribute("orderId", orderId);
            model.addAttribute("payment", order.getPayment());
            //两天后送达
            DateTime dateTime = new DateTime();
            dateTime = dateTime.plusDays(2);
            model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        //订单创建失败
        model.addAttribute("message", result.getMsg());
        return "error/exception";
    }

    @RequestMapping("/my-orders")
    public String show(){
        return "my-orders";
    }
}
