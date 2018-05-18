package com.taotao.service;

import com.taotao.common.pojo.EuTreeNode;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 15:31
 */
public interface ItemCatService {
    List<EuTreeNode> getCatList(long parentId);
}
