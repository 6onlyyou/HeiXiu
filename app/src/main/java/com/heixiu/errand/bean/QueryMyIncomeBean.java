package com.heixiu.errand.bean;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/6/10-10:03
 * Author: fushuaige
 */
public class QueryMyIncomeBean  implements Serializable{
    private String id;
    private String userId;
    private String relaName;
    private String cardNumId;
    private String amountAvailable;

    private String cardStatus;
    private Double amountDay;

    public String getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(String amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public Double getAmountDay() {
        return amountDay;
    }

    public void setAmountDay(Double amountDay) {
        this.amountDay = amountDay;
    }

    public Double getAmountAll() {
        return amountAll;
    }

    public void setAmountAll(Double amountAll) {
        this.amountAll = amountAll;
    }

    private Double amountAll;
    private String zfbId;

    public int getZfbStatus() {
        return zfbStatus;
    }

    public void setZfbStatus(int zfbStatus) {
        this.zfbStatus = zfbStatus;
    }

    private int zfbStatus;
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRelaName() {
        return relaName;
    }

    public void setRelaName(String relaName) {
        this.relaName = relaName;
    }

    public String getCardNumId() {
        return cardNumId;
    }

    public void setCardNumId(String cardNumId) {
        this.cardNumId = cardNumId;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }


    public String getZfbId() {
        return zfbId;
    }

    public void setZfbId(String zfbId) {
        this.zfbId = zfbId;
    }


}
