package com.heixiu.errand.bean;

/**
 * Description:
 * Data：2018/6/14-11:55
 * Author: fushuaige
 */
public class QueryCertificationStatusBean {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getZfbStatus() {
        return zfbStatus;
    }

    public void setZfbStatus(String zfbStatus) {
        this.zfbStatus = zfbStatus;
    }

    private String userId;
    private String cardStatus;
    private String zfbStatus;
}
