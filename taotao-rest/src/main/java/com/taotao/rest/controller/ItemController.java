package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 11:14
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/baseInfo/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBaseInfo(@PathVariable Long itemId){
        TaotaoResult result = itemService.getItemBaseInfo(itemId);
        return result;
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemId){
        TaotaoResult result = itemService.getItemDesc(itemId);
        return result;
    }

    @RequestMapping("/params/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParams(@PathVariable Long itemId){
        TaotaoResult result = itemService.getItemParams(itemId);
        return result;
    }

}
