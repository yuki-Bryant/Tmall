package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 19:48
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws SolrServerException;
}
