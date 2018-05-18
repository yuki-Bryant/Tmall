package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品基本信息管理
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 11:11
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ItemServiceImpl implements ItemService{


    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private JedisClient jedisClient;

    //商品基本信息key
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private int REDIS_ITEM_EXPIRE;

    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        String key = REDIS_ITEM_KEY+":"+itemId+"base";
        //添加缓存逻辑
        //首先从缓存中取
        try {
            String result = jedisClient.get(key);
            if (!StringUtils.isBlank(result)){
                TbItem item = JsonUtils.jsonToPojo(result,TbItem.class);
                return TaotaoResult.ok(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        //缓存中没有就写入缓存
        try {
            jedisClient.set(key, JsonUtils.objectToJson(item));
            jedisClient.expire(key,REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        //使用taotaoresult包装一下
        return TaotaoResult.ok(item);
    }

    @Override
    public TaotaoResult getItemDesc(long itemId) {
        String key = REDIS_ITEM_KEY+":"+itemId+"desc";
        //添加缓存逻辑
        //首先从缓存中取
        try {
            String result = jedisClient.get(key);
            if (!StringUtils.isBlank(result)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(result,TbItemDesc.class);
                return TaotaoResult.ok(itemDesc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        //缓存中没有就写入缓存
        try {
            jedisClient.set(key, JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(key,REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        //使用taotaoresult包装一下
        return TaotaoResult.ok(itemDesc);
    }

    @Override
    public TaotaoResult getItemParams(long itemId) {
        String key = REDIS_ITEM_KEY+":"+itemId+"params";

        //添加缓存逻辑
        //首先从缓存中取
        try {
            String result = jedisClient.get(key);
            if (!StringUtils.isBlank(result)){
                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(result,TbItemParamItem.class);
                return TaotaoResult.ok(itemParamItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //创建查询条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list!=null&&list.size()>0){
            TbItemParamItem itemParamItem = list.get(0);
            //缓存中没有就写入缓存
            try {
                jedisClient.set(key, JsonUtils.objectToJson(itemParamItem));
                jedisClient.expire(key,REDIS_ITEM_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return TaotaoResult.ok(itemParamItem);
        }
        return TaotaoResult.build(400,"无此商品信息");
    }
}
