package com.taotao.service.impl;

import com.taotao.common.pojo.EuTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 15:33
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<EuTreeNode> getCatList(long parentId) {
        //ParentId不是主键，要创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //根据条件查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //转换为Eutreenode List
        List<EuTreeNode> result = new ArrayList<>();
        for (TbItemCat tbItemCat:list){
            EuTreeNode euTreeNode = new EuTreeNode();
            euTreeNode.setId(tbItemCat.getId());
            euTreeNode.setText(tbItemCat.getName());
            //判断是否是父节点
            euTreeNode.setState(tbItemCat.getIsParent()? "closed":"open");
            result.add(euTreeNode);
        }
        return result;
    }
}
