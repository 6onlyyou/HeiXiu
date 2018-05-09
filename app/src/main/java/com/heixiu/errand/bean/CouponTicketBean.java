package com.heixiu.errand.bean;

/**
 * Created by YuanGang on 2018/5/9.
 */

public class CouponTicketBean {

    /**
     * id : 1
     * userId : 15256548233
     * currentId : 9ff5c739311112e88a30d22e443a319b
     * status : 0
     * couponPrice : 5
     * description : 新手有奖
     * startTime : 1525615005000
     * endTime : 1525615005000
     * createTime : 1525615005000
     * updateTime : 1525615005000
     */

    private int id;
    private String userId;
    private String currentId;
    private String status;
    private int couponPrice;
    private String description;
    private long startTime;
    private long endTime;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(int couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
