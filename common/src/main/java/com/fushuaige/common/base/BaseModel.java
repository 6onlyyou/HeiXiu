package com.fushuaige.common.base;

/**
 * Created by zhulinfeng on 2017/4/8.
 */

public class BaseModel<T> {
    private T data;
    private String msg;
    private String state;
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
