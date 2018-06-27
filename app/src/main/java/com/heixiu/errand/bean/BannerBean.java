package com.heixiu.errand.bean;

/**
 * Created by YuanGang on 2018/6/27.
 */

public class BannerBean {

    /**
     * id : 1
     * activityId : 1000
     * title : 测试
     * imgSrc : http://app.heixiuapp.cn:8080/pic/img/1529424348275file.jpg
     * link : http://app.heixiuapp.cn:8080/pic/img/1529424348275file.jpg
     * beginAt : 1530023164000
     * endAt : 1530023164000
     * desc : 测试广告
     * hidden : 1
     */

    private int id;
    private String activityId;
    private String title;
    private String imgSrc;
    private String link;
    private long beginAt;
    private long endAt;
    private String desc;
    private int hidden;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(long beginAt) {
        this.beginAt = beginAt;
    }

    public long getEndAt() {
        return endAt;
    }

    public void setEndAt(long endAt) {
        this.endAt = endAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }
}
