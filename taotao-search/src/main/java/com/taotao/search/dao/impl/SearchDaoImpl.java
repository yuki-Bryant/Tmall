package com.taotao.search.dao.impl;

import com.taotao.common.pojo.Item;
import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索Dao,查找索引库
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 19:21
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;
    @Override
    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {
        SearchResult result = new SearchResult();
        //根据查询条件查询索引库
        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList documentList = response.getResults();
        result.setRecordCount(documentList.getNumFound());
        //取高亮显示
        Map<String,Map<String,List<String>>> highLight = response.getHighlighting();
        //商品列表
        List<Item> list = new ArrayList<>();
        for (SolrDocument document:documentList) {
            Item item = new Item();
            item.setId(Long.valueOf((String) document.get("id")));
            String title = "";
            List<String> lightList = highLight.get(document.get("id")).get("item_title");
            //判断是否为空
            if (lightList!=null&&lightList.size()>0){
                title = lightList.get(0);
            }else {
                title = (String) document.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String) document.get("item_image"));
            item.setPrice((Long) document.get("item_price"));
            item.setSell_point((String) document.get("item_sell_point"));
            item.setCategory_name((String) document.get("item_category_name"));
            list.add(item);
        }
        result.setItemList(list);
        return result;
    }
}
