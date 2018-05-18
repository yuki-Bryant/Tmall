package com.taotao.controller;

import com.taotao.common.pojo.EuTreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 15:48
 */
@Controller
@RequestMapping("item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    //如果id为null是使用默认值，也就是parentid为0的分类列表
    //不提供参数的话默认值是0(首次进去，展示第一层父节点)，不然就是parentId
    public List<EuTreeNode> getCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EuTreeNode> result = itemCatService.getCatList(parentId);
        return result;
    }
}
