package com.heixiu.errand.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PVer on 2018/7/22.
 */

public class PayBean {

    /**
     * package : Sign=WXPay
     * appid : wxfbc7b022388e89cf
     * sign : 40327511B7E16BCBAC89954D4F23494E
     * pre_pay_order_status : success
     * partnerid : 1509234591
     * prepayid : wx22155845485568c13eb2bb1a3275991631
     * noncestr : d4aa4ff55c2d4731ad2c2311d8bffd2d
     * timestamp : 1532246317
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String pre_pay_order_status;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPre_pay_order_status() {
        return pre_pay_order_status;
    }

    public void setPre_pay_order_status(String pre_pay_order_status) {
        this.pre_pay_order_status = pre_pay_order_status;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
