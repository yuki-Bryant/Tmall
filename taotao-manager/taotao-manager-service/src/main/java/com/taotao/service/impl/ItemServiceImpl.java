package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EuDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 9:23
 */
@Service
public class ItemServiceImpl implements ItemService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TbItemMapper itemMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TbItemDescMapper itemDescMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    //对于改动的商品维护solr索引库
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Value("${SEARCH_ALL_SYNC_URL}")
    private String SEARCH_ALL_SYNC_URL;

    @Value("${SEARCH_SINGLE_SYNC_URL}")
    private String SEARCH_SINGLE_SYNC_URL;

    @Override
    public TbItem getItemById(long itemId) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Override
    public EuDataGridResult getItemList(int page, int rows) {
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值
        EuDataGridResult result = new EuDataGridResult();
        result.setRows(list);
        //取total
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem item) {
        //item补全
        //生成商品ID
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemMapper.insert(item);
        //添加索引库同步缓存逻辑
        try {
            HttpClientUtil.doGet(SEARCH_BASE_URL+SEARCH_SINGLE_SYNC_URL);
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
        try {
            //生成商品id
            //可以使用redis的自增长key，在没有redis之前使用时间+随机数策略生成
            Long itemId = IDUtils.genItemId();
            //补全不完整的字段
            item.setId(itemId);
            item.setStatus((byte) 1);
            Date date = new Date();
            item.setCreated(date);
            item.setUpdated(date);
            //把数据插入到商品表
            itemMapper.insert(item);
            //添加商品描述
            TbItemDesc itemDesc = new TbItemDesc();
            itemDesc.setItemDesc(desc);
            itemDesc.setItemId(itemId);
            itemDesc.setCreated(date);
            itemDesc.setUpdated(date);
            //把数据插入到商品描述表
            itemDescMapper.insert(itemDesc);

            //把商品的规格参数插入到tb_item_param_item中
            TbItemParamItem itemParamItem = new TbItemParamItem();
            itemParamItem.setItemId(itemId);
            itemParamItem.setParamData(itemParams);
            itemParamItem.setCreated(date);
            itemParamItem.setUpdated(date);
            itemParamItemMapper.insert(itemParamItem);
            //添加索引库同步缓存逻辑
            //1.每一次添加都再重新导入一遍，太耗时了
            //HttpClientUtil.doGet(SEARCH_BASE_URL+SEARCH_ALL_SYNC_URL);
            //1.每一次添加值添加对应的商品
            //先生成对应的URL
            String url = SEARCH_BASE_URL+SEARCH_SINGLE_SYNC_URL+itemId;
            HttpClientUtil.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
