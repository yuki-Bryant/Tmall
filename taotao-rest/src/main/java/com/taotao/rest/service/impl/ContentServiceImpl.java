package com.taotao.rest.service.impl;

import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 18:46
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public List<TbContent> getContentList(long contentCid) {
        //从缓存中取内容,不能影响正常的业务逻辑
        try {
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY,contentCid+"");
            if (!StringUtils.isBlank(result)){
                //把字符串转换成list
                List<TbContent> list = JsonUtils.jsonToList(result,TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //没有查询到就查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);

        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);

        //向缓存中添加内容,不能影响正常的业务逻辑
        try {
            //需要把list内容转换成String
            String cacheString = JsonUtils.objectToJson(list);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY,contentCid+"",cacheString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
