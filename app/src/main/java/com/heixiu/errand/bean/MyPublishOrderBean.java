package com.heixiu.errand.bean;

import java.util.List;

/**
 * Description:
 * Data：2018/6/10-16:56
 * Author: fushuaige
 */
public class MyPublishOrderBean {
        private List<MyReciecedOrderBean> myReciecedOrderInfos;

    public List<MyReciecedOrderBean> getMyReciecedOrderInfos() {
        return myReciecedOrderInfos;
    }

    public void setMyReciecedOrderInfos(List<MyReciecedOrderBean> myReciecedOrderInfos) {
        this.myReciecedOrderInfos = myReciecedOrderInfos;
    }

    public List<MyReciecedOrderBean> getMyPublishOrderInfos() {
        return myPublishOrderInfos;
    }

    public void setMyPublishOrderInfos(List<MyReciecedOrderBean> myPublishOrderInfos) {
        this.myPublishOrderInfos = myPublishOrderInfos;
    }

    private List<MyReciecedOrderBean> myPublishOrderInfos;
}
