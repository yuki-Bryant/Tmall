package com.taotao.search.testSolr;

import com.taotao.common.pojo.Item;
import com.taotao.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 10:26
 */
public class TestSolr {

    //@Test
    public void TestSolr() throws IOException, SolrServerException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/applicationContext-solr.xml",
                "spring/applicationContext-service.xml","spring/applicationContext-dao.xml"});
        ItemMapper itemMapper = (ItemMapper) context.getBean("itemMapper");
        SolrServer solrServer = (SolrServer) context.getBean("solrServer");
        Item item = itemMapper.getItem(152600459466312L);
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 152600459466312L);
        System.out.println(item.getId());
        document.addField("item_title", item.getTitle());
        System.out.println(item.getTitle());
        document.addField("item_sell_point", item.getSell_point());
        System.out.println(item.getSell_point());
        document.addField("item_price", item.getPrice());
        System.out.println(item.getPrice());
        document.addField("item_image", item.getImage());
        System.out.println(item.getImage());
        document.addField("item_category_name", item.getCategory_name());
        System.out.println(item.getCategory_name());
        document.addField("item_desc", item.getItem_desc());
        solrServer.add(document);
        solrServer.commit();
    }
}
