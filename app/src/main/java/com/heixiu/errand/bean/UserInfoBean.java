package com.heixiu.errand.bean;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/7/10-16:42
 * Author: fushuaige
 */
public class UserInfoBean implements Serializable {
    private int  id;
    private String  userId;
    private String  userName;
    private String  nickName;
    private String  sex;
    private String  userImg;


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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
