package com.heixiu.errand.bean;

import com.fushuaige.common.utils.L;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/6/8-11:36
 * Author: fushuaige
 */
public class DbSubAccountBean implements Serializable {
    private int id;
    private String userId;
    private String relaName;
    private String cardNumId;
    private String cardNumImg;
    private String cardStatus;
    private String amountDay;
    private String amountAll;
    private String zfbId;

    public String getZfbName() {
        return zfbName;
    }

    public void setZfbName(String zfbName) {
        this.zfbName = zfbName;
    }

    private String zfbName;
    private String zfbStatus;
    private String createTime;
    private String updateTime;

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

    public String getCardNumImg() {
        return cardNumImg;
    }

    public void setCardNumImg(String cardNumImg) {
        this.cardNumImg = cardNumImg;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getAmountDay() {
        return amountDay;
    }

    public void setAmountDay(String amountDay) {
        this.amountDay = amountDay;
    }

    public String getAmountAll() {
        return amountAll;
    }

    public void setAmountAll(String amountAll) {
        this.amountAll = amountAll;
    }

    public String getZfbId() {
        return zfbId;
    }

    public void setZfbId(String zfbId) {
        this.zfbId = zfbId;
    }


    public String getZfbStatus() {
        return zfbStatus;
    }

    public void setZfbStatus(String zfbStatus) {
        this.zfbStatus = zfbStatus;
    }
}
