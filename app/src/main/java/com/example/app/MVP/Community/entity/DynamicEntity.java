package com.example.app.MVP.Community.entity;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/4/12-11:25
 * Author: fushuaige
 */
public class DynamicEntity implements Serializable {

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


}
