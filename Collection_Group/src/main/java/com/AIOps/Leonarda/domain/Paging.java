package com.AIOps.Leonarda.domain;

/**
 * @author: leonard lian
 * @description: Memory
 * @date: create in 02:32 2020-04-13
 */
public class Paging {
    private int pageNum;
    private int pageSize;
    private boolean needTotal;

    public boolean isNeedTotal() {
        return needTotal;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setNeedTotal(boolean needTotal) {
        this.needTotal = needTotal;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Paging(int pageNum,int pageSize,boolean needTotal){
        this.pageNum=pageNum;
        this.pageSize=pageSize;
        this.needTotal=needTotal;
    }

    public Paging(int pageSize){
        this(1,pageSize,false);
    }

    public Paging(int pageNum,int pageSize){
        this(pageNum,pageSize,false);
    }





}
