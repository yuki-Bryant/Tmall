package com.taotao.portal.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 21:36
 */
@Service
public class SearchServiceIpml implements SearchService{

    @Value("${SOLR_BASE_URL}")
    private String SOLR_BASE_URL;
    @Override
    public SearchResult search(String queryString, int page) {
        Map<String,String> map = new HashMap<>();
        map.put("q",queryString);
        map.put("page",page+"");
        String result = HttpClientUtil.doGet(SOLR_BASE_URL,map);
        //将字符串转换成java对象
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result,SearchResult.class);
        if (taotaoResult.getStatus()==200){
            SearchResult searchResult = (SearchResult) taotaoResult.getData();
            return searchResult;
        }
        return null;
    }
}
