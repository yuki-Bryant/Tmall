package com.taotao.search.testSolr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/17
 * Time: 15:16
 */
public class solrCloudTest {

    @Test
    public void testAddDocument() throws Exception{
        //创建连接
        String zkHost = "192.168.149.128:2181,192.168.149.128:2182,192.168.149.128:2183";
        CloudSolrServer solrServer = new CloudSolrServer(zkHost);
        //设置默认的collection
        solrServer.setDefaultCollection("collection1");
        //创建文本对象
        SolrInputDocument document = new SolrInputDocument();
        //向文本添加域
        document.addField("id","test002");
        document.addField("item_title","测试集群2");
        //提交到索引库
        solrServer.add(document);
        solrServer.commit();
        System.out.println("提交成功");
    }

    // 搜索索引
    @Test
    public void testSearchIndexFromSolrCloud() throws Exception {
        //创建连接
        String zkHost = "192.168.149.128:2181,192.168.149.128:2182,192.168.149.128:2183";
        CloudSolrServer solrServer = new CloudSolrServer(zkHost);
        //设置默认的collection
        solrServer.setDefaultCollection("collection1");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        try {
            QueryResponse response = solrServer.query(query);
            SolrDocumentList docs = response.getResults();

            System.out.println("文档个数：" + docs.getNumFound());
            System.out.println("查询时间：" + response.getQTime());

            for (SolrDocument doc : docs) {
                ArrayList title = (ArrayList) doc.getFieldValue("title");
                String id = (String) doc.getFieldValue("id");
                System.out.println("id: " + id);
                System.out.println("title: " + title);
                System.out.println();
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknowned Exception!!!!");
            e.printStackTrace();
        }
    }

}
