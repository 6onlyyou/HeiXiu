package com.heixiu.errand.bean;

import java.io.Serializable;

/**
 * Description:
 * Data：2018/6/8-11:36
 * Author: fushuaige
 */
public class SelectDataByIdBean implements Serializable {
    private  UserInfo userInfo;

    private DbSubAccountBean dbSubAccount;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public DbSubAccountBean getDbSubAccount() {
        return dbSubAccount;
    }

    public void setDbSubAccount(DbSubAccountBean dbSubAccount) {
        this.dbSubAccount = dbSubAccount;
    }
}
