package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EuDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbItem;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/8
 * Time: 16:11
 */
@Service
public class ContentServiceIpml implements ContentService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TbContentMapper contentMapper;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    @Override
    public EuDataGridResult getItemList(int page, int rows, long categoryId) {
        TbContentExample example = new TbContentExample();
        //设置分页
        PageHelper.startPage(page,rows);
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        //创建返回值
        EuDataGridResult result = new EuDataGridResult();
        result.setRows(list);
        //取total
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addContent(TbContent tbContent) {
        //补全pojo
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        contentMapper.insert(tbContent);

        //添加内容同步缓存逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult editContent(TbContent tbContent) {
        contentMapper.updateByPrimaryKey(tbContent);

        //添加内容同步缓存逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(long ids) {
        TbContent tbContent = contentMapper.selectByPrimaryKey(ids);
        //添加同步缓存逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
        }catch (Exception e){
            e.printStackTrace();
        }
        contentMapper.deleteByPrimaryKey(ids);
        return TaotaoResult.ok();
    }
}
