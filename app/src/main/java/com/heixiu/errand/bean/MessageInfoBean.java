package com.heixiu.errand.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2018/6/8-10:31
 * Author: fushuaige
 */
public class MessageInfoBean implements Serializable {
 private UserInfo userInfo;
    private int userFollowsCount;
    private int userFanCounts;
    private int orderInfoReceiveCount;
    private int orderInfoPublishCount;
    private int followStatus;
    private int admireCount;

    public int getAdmireCount() {
        return admireCount;
    }

    public void setAdmireCount(int admireCount) {
        this.admireCount = admireCount;
    }

    public List<PubLishInfo> getPublishInfos() {
        return publishInfos;
    }
    public void setPublishInfos(List<PubLishInfo> publishInfos) {
        this.publishInfos = publishInfos;
    }

    private List<PubLishInfo> publishInfos;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getUserFollowsCount() {
        return userFollowsCount;
    }

    public void setUserFollowsCount(int userFollowsCount) {
        this.userFollowsCount = userFollowsCount;
    }

    public int getUserFanCounts() {
        return userFanCounts;
    }

    public void setUserFanCounts(int userFanCounts) {
        this.userFanCounts = userFanCounts;
    }

    public int getOrderInfoReceiveCount() {
        return orderInfoReceiveCount;
    }

    public void setOrderInfoReceiveCount(int orderInfoReceiveCount) {
        this.orderInfoReceiveCount = orderInfoReceiveCount;
    }

    public int getOrderInfoPublishCount() {
        return orderInfoPublishCount;
    }

    public void setOrderInfoPublishCount(int orderInfoPublishCount) {
        this.orderInfoPublishCount = orderInfoPublishCount;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

}
