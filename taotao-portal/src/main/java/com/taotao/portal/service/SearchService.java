package com.taotao.portal.service;

import com.taotao.common.pojo.SearchResult;
/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 21:35
 */
public interface SearchService {
    SearchResult search(String queryString,int page);
}
