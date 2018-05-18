package com.taotao.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 19:20
 */
public class HttpClientTest {

    //get不带参数
    @Test
    public void doGet() throws Exception{
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个GET对象
        HttpGet get = new HttpGet("http://www.baidu.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statuscode = response.getStatusLine().getStatusCode();
        System.out.println(statuscode);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity,"utf-8");
        System.out.println(s);
        //关闭httpclient
        response.close();
        httpClient.close();
    }

    //get带参数
    @Test
    public void doGetWithParam() throws Exception{
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个URI对象，GET对象
        URIBuilder uriBuilder = new URIBuilder("http://www.sougou.com/web");
        uriBuilder.addParameter("query","花千骨");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statuscode = response.getStatusLine().getStatusCode();
        System.out.println(statuscode);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity,"utf-8");
        System.out.println(s);
        //关闭httpclient
        response.close();
        httpClient.close();
    }

    //post不带参数
    @Test
    public void doPost() throws Exception{
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象,注意.html不能响应json数据类型
        HttpPost httpPost = new HttpPost("http://localhost:8082/httpclient/post.action");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println(string);
        response.close();
        httpClient.close();
    }

    //post带参数
    @Test
    public void doPostWithParam() throws Exception{
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象
        HttpPost httpPost = new HttpPost("http://localhost:8082/httpclient/post.action");
        //创建一个entity,模拟表单
        List<NameValuePair> entity = new ArrayList<>();
        entity.add(new BasicNameValuePair("username","张三"));
        entity.add(new BasicNameValuePair("password","123"));
        StringEntity stringEntity = new UrlEncodedFormEntity(entity,"UTF-8");
        //执行请求
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String string = EntityUtils.toString(response.getEntity(),"UTF-8");
        System.out.println(string);
        response.close();
        httpClient.close();
    }
}
