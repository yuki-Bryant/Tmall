package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RediseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/9
 * Time: 18:49
 */
@Service
public class RedisServiceImpl implements RediseService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public TaotaoResult syncContent(long contentCid) {
        try {
            jedisClient.hdelete(INDEX_CONTENT_REDIS_KEY,contentCid+"");

        }catch (Exception e){
            e.printStackTrace();
            TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
