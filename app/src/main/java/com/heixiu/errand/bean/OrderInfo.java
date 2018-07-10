package com.heixiu.errand.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by YuanGang on 2018/5/4.
 */

public class OrderInfo implements Serializable {


    private static final long serialVersionUID = 6510760223886357972L;
    /**
     * id : 5
     * orderNum : d5b2912d-d66f-4b0f-984b-1d22d4543c2a
     * userId : 12
     * receiveId : null
     * courierNum : 1385555
     * receiveNum : 1367744444
     * receiveName : 黎明
     * payment : 25
     * paymentType : 0
     * addPrice : 10
     * supportPrice : 2
     * orderStatus : 0
     * paymentTime : 2018-05-02 21:19:48.0
     * sendTime : 2018-05-02 21:19:48.0
     * weight : 1
     * name : 手机
     * description : 东西很重要
     * sendAddress : 上海南站
     * receiveAddress : 徐家汇
     * originsLatitude : 0
     * originsLongitude : 0
     * destinationsLatitude : 0
     * destinationsLongitude : 0
     * createTime : 1525267188000
     * updateTime : 1525267188000
     */

    private int id;
    private String orderNum;
    private String userId;
    private Object receiveId;
    private String courierNum;
    private String receiveNum;
    private String receiveName;
    private int payment;
    private String paymentType;
    private int addPrice;
    private int supportPrice;
    private String orderStatus;
    private String paymentTime;
    private String sendTime;
    private int weight;
    private String name;
    @SerializedName("descriptions")
    private String description;
    private String sendAddress;
    private String receiveAddress;
    private double originsLatitude;
    private double originsLongitude;
    private double destinationsLatitude;
    private double destinationsLongitude;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Object receiveId) {
        this.receiveId = receiveId;
    }

    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
    }

    public String getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(String receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(int addPrice) {
        this.addPrice = addPrice;
    }

    public int getSupportPrice() {
        return supportPrice;
    }

    public void setSupportPrice(int supportPrice) {
        this.supportPrice = supportPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public double getOriginsLatitude() {
        return originsLatitude;
    }

    public void setOriginsLatitude(double originsLatitude) {
        this.originsLatitude = originsLatitude;
    }

    public double getOriginsLongitude() {
        return originsLongitude;
    }

    public void setOriginsLongitude(double originsLongitude) {
        this.originsLongitude = originsLongitude;
    }

    public double getDestinationsLatitude() {
        return destinationsLatitude;
    }

    public void setDestinationsLatitude(double destinationsLatitude) {
        this.destinationsLatitude = destinationsLatitude;
    }

    public double getDestinationsLongitude() {
        return destinationsLongitude;
    }

    public void setDestinationsLongitude(double destinationsLongitude) {
        this.destinationsLongitude = destinationsLongitude;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
