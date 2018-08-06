package com.heixiu.errand.bean;

import java.util.List;

/**
 * Description:
 * Data：2018/6/10-16:56
 * Author: fushuaige
 */
public class MyPublishOrderBean {
    private List<OrderInfo> myReciecedOrderInfos;

    public List<OrderInfo> getMyReciecedOrderInfos() {
        return myReciecedOrderInfos;
    }

    public void setMyReciecedOrderInfos(List<OrderInfo> myReciecedOrderInfos) {
        this.myReciecedOrderInfos = myReciecedOrderInfos;
    }

    public List<OrderInfo> getMyPublishOrderInfos() {
        return myPublishOrderInfos;
    }

    public void setMyPublishOrderInfos(List<OrderInfo> myPublishOrderInfos) {
        this.myPublishOrderInfos = myPublishOrderInfos;
    }

    private List<OrderInfo> myPublishOrderInfos;
}
