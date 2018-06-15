package com.heixiu.errand.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YuanGang on 2018/6/15.
 */

public class PackageInformationBean implements Serializable{

    /**
     * LogisticCode : 70442548119300
     * ShipperCode : HTKY
     * Traces : [{"AcceptStation":"苏州市【常熟古里镇二部】，【王成飞/13013827618】已揽收","AcceptTime":"2018-05-25 20:23:46"},{"AcceptStation":"到苏州市【常熟东南经开区集货点】","AcceptTime":"2018-05-25 23:13:45"},{"AcceptStation":"到苏州市【常熟集散中心】","AcceptTime":"2018-05-26 19:42:28"},{"AcceptStation":"苏州市【常熟集散中心】，正发往【上海转运中心】","AcceptTime":"2018-05-27 00:07:37"},{"AcceptStation":"到上海市【上海转运中心】","AcceptTime":"2018-05-27 02:02:45"},{"AcceptStation":"上海市【上海转运中心】，正发往【徐汇三部】","AcceptTime":"2018-05-27 05:14:51"},{"AcceptStation":"到上海市【徐汇三部】","AcceptTime":"2018-05-27 07:26:55"},{"AcceptStation":"上海市【徐汇三部】，【卢锋/13127670368】正在派件","AcceptTime":"2018-05-27 18:53:48"}]
     * State : 2
     * EBusinessID : 1334346
     * Success : true
     */

    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String EBusinessID;
    private boolean Success;
    private List<TracesBean> Traces;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean implements Serializable {
        /**
         * AcceptStation : 苏州市【常熟古里镇二部】，【王成飞/13013827618】已揽收
         * AcceptTime : 2018-05-25 20:23:46
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}
