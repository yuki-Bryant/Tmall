package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;

import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 19:50
 */
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult search(String queryString, int page, int rows) throws SolrServerException {
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //需要考虑查询条件为空，可以采取默认值
        query.setQuery(queryString);
        //设置分页
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_keywords");
        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult result = searchDao.search(query);
        //计算总页数
        long recordCount = result.getRecordCount();
        long pageConut =  recordCount/rows;
        if (recordCount%rows>0){
            pageConut++;
        }
        result.setPageCount(pageConut);
        result.setCurPage(page);
        return result;
    }
}
