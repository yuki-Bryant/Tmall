package com.taotao.controller;

import com.taotao.common.pojo.EuTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 11:13
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {

    @Autowired
    private ContentCatService contentCatService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EuTreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long paretnId){
        List<EuTreeNode> result = contentCatService.getCategoryList(paretnId);
        return result;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createNode(Long parentId,String name){
        TaotaoResult result = contentCatService.insertContentCatgory(parentId,name);
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteNode(Long parentId,Long id){
        TaotaoResult result = contentCatService.deleteContentCatgory(id);
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult update(Long id,String name){
        TaotaoResult result = contentCatService.updateContentCategory(id,name);
        return result;
    }
}
