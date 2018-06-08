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
    private String zfbPassword;
    private String zfbStatus;
    private Long createTime;
    private Long updateTime;

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

    public String getZfbPassword() {
        return zfbPassword;
    }

    public void setZfbPassword(String zfbPassword) {
        this.zfbPassword = zfbPassword;
    }

    public String getZfbStatus() {
        return zfbStatus;
    }

    public void setZfbStatus(String zfbStatus) {
        this.zfbStatus = zfbStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
