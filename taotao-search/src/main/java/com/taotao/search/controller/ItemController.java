package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * solr索引库维护
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 17:16
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //导入所有商品数据到索引库
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems(){
        try {
            TaotaoResult result = itemService.ImportAllItems();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    //导入新增商品数据更新索引库
    @RequestMapping("/import/{id}")
    @ResponseBody
    public TaotaoResult importItem(@PathVariable Long id){
        try {
            TaotaoResult result = itemService.ImportItem(id);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
