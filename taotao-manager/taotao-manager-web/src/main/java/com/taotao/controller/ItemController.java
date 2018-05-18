package com.taotao.controller;

import com.taotao.common.pojo.EuDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 9:31
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable(value ="itemId") long itemId){
        TbItem item = itemService.getItemById(itemId);
        return item;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EuDataGridResult getItemList(Integer page,Integer rows){
        EuDataGridResult result = itemService.getItemList(page,rows);
        return result;
    }

    @RequestMapping(value="/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem item,String desc,String itemParams){
        TaotaoResult result = itemService.addItem(item,desc,itemParams);
        return result;
    }
}
