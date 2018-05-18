package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/7
 * Time: 9:44
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult checkItemParam(@PathVariable Long cid) {
        TaotaoResult result = itemParamService.checkParam(cid);
        return result;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult addItemParam(@PathVariable Long cid, String paramData) {
        TaotaoResult result = itemParamService.addItemParam(cid, paramData);
        return result;
    }

    @RequestMapping("/cid/{cid}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long cid) {
        TaotaoResult result = itemParamService.getItemParemByCid(cid);
        return result;
    }
}
