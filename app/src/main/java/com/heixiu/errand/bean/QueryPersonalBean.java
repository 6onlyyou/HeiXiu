package com.heixiu.errand.bean;

import java.io.Serializable;

/**
 * Description:
 * 	"platRank": 0,
 "friendRank": 0,
 "userImg": "",
 "dayAmount": null,
 "rankPlatInfoList": null

 * Data：2018/6/7-17:18
 * Author: fushuaige
 */
public class QueryPersonalBean implements Serializable {

    private int platRank;
    private int friendRank;

    public int getPlatRank() {
        return platRank;
    }

    public void setPlatRank(int platRank) {
        this.platRank = platRank;
    }

    public int getFriendRank() {
        return friendRank;
    }


    public void setFriendRank(int friendRank) {
        this.friendRank = friendRank;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(String dayAmount) {
        this.dayAmount = dayAmount;
    }

    public String getRankPlatInfoList() {
        return rankPlatInfoList;
    }

    public void setRankPlatInfoList(String rankPlatInfoList) {
        this.rankPlatInfoList = rankPlatInfoList;
    }

    private String userImg;
    private String dayAmount;
    private String rankPlatInfoList;
}
