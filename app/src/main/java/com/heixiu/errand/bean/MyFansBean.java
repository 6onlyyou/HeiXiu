package com.heixiu.errand.bean;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/6/8-16:54
 * Author: fushuaige
 */
public class MyFansBean implements Serializable {
    private String followStatus;
  private UserInfoBean userInfo;

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }
}
