package com.heixiu.errand.net;

/**
 * Description:
 * Data：2018/5/9-10:53
 * Author: fushuaige
 */
public class MyException extends Exception {
    /**
     * code : 200
     * message : success
     * isSuccess : true
     * data : {"token":"45AE2E153CE860B544D785A6BDB7CAB6"}
     */
    //无参构造方法
    public MyException(){

        super();
    }

    //有参的构造方法
    public MyException(String message){
        super(message);
        this.message = message;
    }
    private int code;
    private String message;
    private boolean isSuccess;

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

}
