package com.taotao.common.pojo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 14:15
 */
public class EuDataGridResult {
    //返回一个easyUi的数据格式对象
    private long total;
    private List<?> rows;//指定为泛型

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
