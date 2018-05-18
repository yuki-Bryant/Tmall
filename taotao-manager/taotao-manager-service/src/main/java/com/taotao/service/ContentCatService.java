package com.taotao.service;

import com.taotao.common.pojo.EuTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 11:00
 */
public interface ContentCatService {
    List<EuTreeNode> getCategoryList(long parentId);
    TaotaoResult insertContentCatgory(long parentId,String name);
    TaotaoResult deleteContentCatgory(long id);
    TaotaoResult updateContentCategory(long id,String name);


}
