package com.taotao.controller;

import com.taotao.common.pojo.EuDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 16:17
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EuDataGridResult getContentList(Integer page,Integer rows,Long categoryId){
        EuDataGridResult result = contentService.getItemList(page,rows,categoryId);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addContent(TbContent tbContent){
        TaotaoResult result = contentService.addContent(tbContent);
        return result;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult editContent(TbContent tbContent){
        TaotaoResult result = contentService.editContent(tbContent);
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContent(Long ids){
        TaotaoResult result = contentService.deleteContent(ids);
        return result;
    }
}
