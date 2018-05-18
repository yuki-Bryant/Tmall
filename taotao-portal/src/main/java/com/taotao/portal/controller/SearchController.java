package com.taotao.portal.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/10
 * Time: 21:51
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page, Model model){
        if (queryString!=null){
            try {
                queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        SearchResult searchResult = searchService.search(queryString,page);
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",searchResult.getRecordCount());
        model.addAttribute("itemList",searchResult.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}
