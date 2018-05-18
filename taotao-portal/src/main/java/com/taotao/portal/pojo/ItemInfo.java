package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/11
 * Time: 15:56
 */
public class ItemInfo extends TbItem{
    //添加此方法拆分图片列表
    public String[] getImages() {
        String image = getImage();
        if (image != null && image != "") {
            String[] strings = image.split(",");
            return strings;
        }
        return null;
    }
}
