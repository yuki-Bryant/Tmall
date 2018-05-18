package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 17:32
 */
public interface ContentService {
    List<TbContent> getContentList(long contentCid);
}
