package com.heixiu.errand.bean;

/**
 * Created by YuanGang on 2018/5/4.
 */

public  class ListCommentInfoBean {
    /**
     * id : 1
     * userId : 22
     * commentId :
     * admireId : null
     * publishId : b716f6c547ee4fc388bc0f7254ce7961
     * content : sbsbsbbsbsb
     * nickName : rr
     * userImg : www.ww
     */

    private int id;
    private String userId;
    private String commentId;
    private Object admireId;
    private String publishId;
    private String content;
    private String nickName;
    private String userImg;

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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Object getAdmireId() {
        return admireId;
    }

    public void setAdmireId(Object admireId) {
        this.admireId = admireId;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}