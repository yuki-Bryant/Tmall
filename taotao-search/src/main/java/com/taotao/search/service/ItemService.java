package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 16:47
 */
public interface ItemService {
    TaotaoResult ImportAllItems() throws IOException, SolrServerException;
    TaotaoResult ImportItem(long item_id) throws IOException, SolrServerException;
}
