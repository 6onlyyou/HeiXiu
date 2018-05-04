package com.heixiu.errand.bean;

/**
 * Created by YuanGang on 2018/5/4.
 */

public class ResponseBean<T> {

    /**
     * code : 200
     * message : success
     * isSuccess : true
     * data : {"token":"45AE2E153CE860B544D785A6BDB7CAB6"}
     */

    private int code;
    private String message;
    private boolean isSuccess;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
