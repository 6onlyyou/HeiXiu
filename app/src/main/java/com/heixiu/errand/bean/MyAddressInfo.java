package com.heixiu.errand.bean;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/6/4-11:05
 * Author: fushuaige
 */
public class MyAddressInfo implements Serializable {
    /**
     *  "id": 1,
     "userId": "12",
     "receiveName": "黎明",
     "receiveNum": "13566666",
     "area": "徐汇",
     "street": "龙川路",
     "detail": "67号",
     "addressStatus": "0",
     "createTime": 1525568619000,
     "updateTime": 1525568619000

     */
    private int id;
    private String userId;
    private String receiveName;
    private String receiveNum;
    private String area;
    private String street;
    private String detail;
    private String addressStatus;
    private Long createTime;
    private Long updateTime;

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    private String editType;
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

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(String receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
