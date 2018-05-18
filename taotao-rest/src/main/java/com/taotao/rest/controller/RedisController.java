package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RediseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/9
 * Time: 18:56
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {
    @Autowired
    private RediseService rediseService;

    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public TaotaoResult contentCacheSync(@PathVariable Long contentCid){
        TaotaoResult result = rediseService.syncContent(contentCid);
        return result;
    }
}
