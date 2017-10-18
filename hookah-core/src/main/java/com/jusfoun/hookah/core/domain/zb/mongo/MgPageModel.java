package com.jusfoun.hookah.core.domain.zb.mongo;

import java.util.List;

/**
 * mongodb 分页基础类
 */
public class MgPageModel<T> {

    //结果集
    private List<T> list;

    //查询记录数
    private int rowCount;

    //每页多少条数据
    private int pageSize = 10;

    //第几页
    private int pageNum = 1;

    //跳过几条数
    private int skip = 0;

    // 总页数
    public int getTotalPages() {
        return (rowCount + pageSize - 1) / pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getSkip() {
        skip = (pageNum - 1) * pageSize;
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }
}
