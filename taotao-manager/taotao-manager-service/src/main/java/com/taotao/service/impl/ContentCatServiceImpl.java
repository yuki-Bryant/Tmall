package com.taotao.service.impl;

import com.taotao.common.pojo.EuTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 11:02
 */
@Service
public class ContentCatServiceImpl implements ContentCatService{


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EuTreeNode> getCategoryList(long parentId) {
        //创建查询条件
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EuTreeNode> result = new ArrayList<>();
        for(TbContentCategory tbContentCategory:list){
            EuTreeNode node = new EuTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            result.add(node);
        }
        return result;
    }

    @Override
    public TaotaoResult insertContentCatgory(long parentId, String name) {
        Date date = new Date();
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setStatus(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);

        //插入到数据库中,记得修改mapper文件插入代码，获取自增长主键的id
        contentCategoryMapper.insert(tbContentCategory);
        //由于插入了新的结点，所以之前的父节点就要更新一遍，更新父节点
        //取父节点
        TbContentCategory dataCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!dataCategory.getIsParent()){
            dataCategory.setIsParent(true);
            //更新父节点isParent列
            contentCategoryMapper.updateByPrimaryKey(dataCategory);
        }
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public TaotaoResult deleteContentCatgory(long id) {
        //执行删除
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        deleteContentCat(id);

        //设置查询条件，查询该节点父节点所有的子节点，判断该节点的父节点是否没有子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(tbContentCategory.getParentId());
        List<TbContentCategory> children = contentCategoryMapper.selectByExample(example);
        TbContentCategory father = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        if (children.size()==0){
            father.setIsParent(false);
            //更新父节点isParent列
            contentCategoryMapper.updateByPrimaryKey(father);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategory(long id, String name) {
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        return TaotaoResult.ok();
    }

    //删除结点方法
    public void deleteContentCat(long id){
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        //判断是否为父节点,递归
        if (tbContentCategory.getIsParent()){
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            //查询所有父节点为该节点的结点
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory tbContentCategory1:list){
                deleteContentCat(tbContentCategory.getId());
            }
        }
        contentCategoryMapper.deleteByPrimaryKey(id);
    }
}
