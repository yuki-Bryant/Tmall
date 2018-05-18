package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品信息管理
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 15:31
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;

    @Value("${ITEM_PARAMS_URL}")
    private String ITEM_PARAMS_URL;

    @Override
    public ItemInfo getItemById(long itemId) {
        String url = REST_BASE_URL+ITEM_INFO_URL+itemId;
        try {
            String result = HttpClientUtil.doGet(url);
            if (!StringUtils.isBlank(result)){
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result,ItemInfo.class);
                if (taotaoResult.getStatus()==200){
                    ItemInfo item = (ItemInfo) taotaoResult.getData();
                    return item;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemDesc(long itemId) {
        String url = REST_BASE_URL+ITEM_DESC_URL+itemId;
        try {
            String result = HttpClientUtil.doGet(url);
            if (!StringUtils.isBlank(result)){
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, TbItemDesc.class);
                if (taotaoResult.getStatus()==200){
                    TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
                    return itemDesc.getItemDesc();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemParams(long itemId) {
        String url = REST_BASE_URL+ITEM_PARAMS_URL+itemId;
        StringBuilder html = new StringBuilder();
        try {
            String result = HttpClientUtil.doGet(url);
            if (!StringUtils.isBlank(result)){
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, TbItemParamItem.class);
                if (taotaoResult.getStatus()==200){
                    TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
                    String params = itemParamItem.getParamData();
                    //把规格参数转换成java对象
                    List<Map> paramList = JsonUtils.jsonToList(params, Map.class);
                    html.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
                    html.append("     <tbody>\n");
                    for(Map param:paramList) {
                        html.append("          <tr>\n");
                        html.append("               <th class=\"tdTitle\" colspan=\"2\">"+param.get("group")+"</th>\n");
                        html.append("          </tr>\n");
                        //取规格项
                        List<Map> object = (List<Map>) param.get("params");
                        for (Map map : object) {
                            html.append("          <tr>\n");
                            html.append("               <td class=\"tdTitle\">"+map.get("k")+"</td>\n");
                            html.append("               <td>"+map.get("v")+"</td>\n");
                            html.append("          </tr>\n");
                        }
                    }
                    html.append("     </tbody>\n");
                    html.append("</table>");
                }
                return html.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
