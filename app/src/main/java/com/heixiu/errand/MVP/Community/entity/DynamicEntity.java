package com.heixiu.errand.MVP.Community.entity;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/4/12-11:25
 * Author: fushuaige
 */
public class DynamicEntity implements Serializable {
    private String headurl;
    private String nickname;
    private String praise;
    private String comment;
    private String pictureUrl;
    private String title;
    private String introduction;
    private String viodeoUrl;
    private int id;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getViodeoUrl() {
        return viodeoUrl;
    }

    public void setViodeoUrl(String viodeoUrl) {
        this.viodeoUrl = viodeoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
