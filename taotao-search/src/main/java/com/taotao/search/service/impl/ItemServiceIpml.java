package com.taotao.search.service.impl;

import com.taotao.common.pojo.Item;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 16:50
 */
@Service
public class ItemServiceIpml implements ItemService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public TaotaoResult ImportAllItems() throws IOException, SolrServerException {
        //读取商品列表
        List<Item> list = itemMapper.getItemList();
        //写入索引库
        for (Item item:list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            document.addField("item_desc", item.getItem_desc());
            solrServer.add(document);
        }
        solrServer.commit();
        return TaotaoResult.ok();
    }

    //添加单个商品
    @Override
    public TaotaoResult ImportItem(long item_id) throws IOException, SolrServerException {
        Item item = itemMapper.getItem(item_id);
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", item_id);
        document.addField("item_title", item.getTitle());
        document.addField("item_sell_point", item.getSell_point());
        document.addField("item_price", item.getPrice());
        document.addField("item_image", item.getImage());
        document.addField("item_category_name", item.getCategory_name());
        document.addField("item_desc", item.getItem_desc());
        solrServer.add(document);
        solrServer.commit();
        return TaotaoResult.ok();
    }
}
