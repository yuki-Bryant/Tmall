package com.taotao.search.dao;

import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 19:20
 */
public interface SearchDao {
    SearchResult search(SolrQuery solrQuery) throws SolrServerException;
}
