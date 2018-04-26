package com.fushuaige.common.base;

import java.util.List;

/**
 * Created by zhulinfeng on 2017/4/8.
 */

public class ListModel<T> {
    private String isPage;
    private List<T> data;
    private int rowNum;
    private int totalPageNum;
    private int pageNum;

    public String getIsPage() {
        return isPage;
    }

    public void setIsPage(String isPage) {
        this.isPage = isPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
