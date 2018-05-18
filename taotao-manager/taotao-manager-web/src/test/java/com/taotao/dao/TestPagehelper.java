package com.taotao.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 11:12
 */
public class TestPagehelper {
    @Test
    public void testPagehelper(){
        //加载spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //取mapper对象
        TbItemMapper itemMapper = context.getBean(TbItemMapper.class);
        //执行查询，并分页
        TbItemExample example = new TbItemExample();
        PageHelper.startPage(1,10);
        List<TbItem> list = itemMapper.selectByExample(example);
        for (TbItem tbItem:list){
            System.out.println(tbItem.getTitle());
        }
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        long total = pageInfo.getTotal();
        System.out.println("共有商品：" +total);
    }
}
