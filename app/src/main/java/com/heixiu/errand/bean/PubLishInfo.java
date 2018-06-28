package com.heixiu.errand.bean;

import java.util.List;

/**
 * Created by YuanGang on 2018/5/4.
 */

public class PubLishInfo {

    /**
     * id : 5
     * userId : 22
     * publishId : b716f6c547ee4fc388bc0f7254ce7961
     * title : 00
     * content : 00
     * type : 0
     * contentImg : null
     * contentVideo : http://app.chemide.com/dateimg/img/15243735249781.jpg
     * admireCount : 2
     * commentCount : 2
     * nickName : rr
     * userImg : www.ww
     * listCommentInfo : null
     */

    private int id;
    private int followStatus;
    private int admireStatus;
    private String userId;
    private String publishId;
    private String title;
    private String content;
    private String type;
    private Object contentImg;
    private String contentVideo;
    private int admireCount;
    private int commentCount;
    private String nickName;
    private String userImg;
    private Object listCommentInfo;
    private Long createTime;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public List<String> getContentImgList() {
        return contentImgList;
    }

    public void setContentImgList(List<String> contentImgList) {
        this.contentImgList = contentImgList;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public int getAdmireStatus() {
        return admireStatus;
    }

    public void setAdmireStatus(int admireStatus) {
        this.admireStatus = admireStatus;
    }

    private List<String> contentImgList;

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

    public Object getContentImg() {
        return contentImg;
    }

    public void setContentImg(Object contentImg) {
        this.contentImg = contentImg;
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

    public Object getListCommentInfo() {
        return listCommentInfo;
    }

    public void setListCommentInfo(Object listCommentInfo) {
        this.listCommentInfo = listCommentInfo;
    }
}
