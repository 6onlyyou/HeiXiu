package com.heixiu.errand.bean;

import java.io.Serializable;
import java.util.List;

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

    private static final long serialVersionUID = 7846280459532412918L;
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



    private String userImg;

    public int getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(int dayAmount) {
        this.dayAmount = dayAmount;
    }

    private int dayAmount;
}
