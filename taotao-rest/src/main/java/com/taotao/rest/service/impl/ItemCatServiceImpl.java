package com.taotao.rest.service.impl;

import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 9:14
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CATEGORY_REDIS_KEY}")
    private String CATEGORY_REDIS_KEY;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        //首先从缓存中查找
        //创建返回结果
        if (getCatListFromRedis(0)!=null){
            catResult.setData(getCatListFromRedis(0));
        }else {
            catResult.setData(getCatList(0));
            //添加到缓存
            addCatToRedis(getCatList(0));
        }
        return catResult;
    }

    public List<?> getCatList(long parentId){
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //查询结果
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List result = new ArrayList();
        //计数器
        int count = 0;
        for (TbItemCat tbItemCat:list){
            //判断是否是父节点
            if (tbItemCat.getIsParent()){
                CatNode node = new CatNode();
                node.setUrl("/products/" + tbItemCat.getId() + ".html");
                //判断是否为第一层节点
                if (parentId == 0) {
                    node.setName("<a href='"+node.getUrl()+"'>"+tbItemCat.getName()+"</a>");
                } else {
                    node.setName(tbItemCat.getName());
                }
                node.setItem(getCatList(tbItemCat.getId()));
                result.add(node);
            }else {
                String node = "/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName();
                result.add(node);
            }
            count++;
            //第一个层循环，只取14条记录
            if (parentId == 0 && count >= 14) {
                break;
            }
        }
        return result;
    }

    //从缓存中查询
    public List<?> getCatListFromRedis(long parentId){
        //从缓存中取内容,不能影响正常的业务逻辑
        try {
            String result = jedisClient.hget(CATEGORY_REDIS_KEY,parentId+"");
            if (!StringUtils.isBlank(result)){
                //把字符串转换成list
                List<?> list = JsonUtils.jsonToList(result,CatNode.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //向缓存中添加
    public void addCatToRedis(List<?> list){
        //向缓存中添加内容,不能影响正常的业务逻辑
        try {
            //需要把list内容转换成String
            String cacheString = JsonUtils.objectToJson(list);
            jedisClient.hset(CATEGORY_REDIS_KEY,0+"",cacheString);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
