package com.heixiu.errand.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Data：2018/6/8-10:39
 * Author: fushuaige
 */
public class PublishInfoBean implements Serializable {
    private int id;
    private String userId;
    private String publishId;
    private String title;
    private String content;
    private String type;
    private String contentVideo;
    private int admireCount;
    private int commentCount;
    private String nickName;
    private String userImg;


    public List<String> getContentImgList() {
        return contentImgList;
    }

    public void setContentImgList(List<String> contentImgList) {
        this.contentImgList = contentImgList;
    }

    private List<String> contentImgList;
    private String listCommentInfo;

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

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getContentVideo() {
        return contentVideo;
    }

    public void setContentVideo(String contentVideo) {
        this.contentVideo = contentVideo;
    }

    public int getAdmireCount() {
        return admireCount;
    }

    public void setAdmireCount(int admireCount) {
        this.admireCount = admireCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }


    public String getListCommentInfo() {
        return listCommentInfo;
    }

    public void setListCommentInfo(String listCommentInfo) {
        this.listCommentInfo = listCommentInfo;
    }
}
