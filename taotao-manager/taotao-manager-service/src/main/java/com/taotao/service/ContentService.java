package com.taotao.service;

import com.taotao.common.pojo.EuDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 16:10
 */
public interface ContentService {
    EuDataGridResult getItemList(int page, int rows,long categoryId);
    TaotaoResult addContent(TbContent tbContent);

    TaotaoResult editContent(TbContent tbContent);
    TaotaoResult deleteContent(long ids);
}
