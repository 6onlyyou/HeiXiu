package com.fushuaige.common.net.transformer;

/**
 * Created by zhulinfeng on 2017/4/9.
 */

public class APIException extends RuntimeException {
    private int code;


    public APIException(String message) {
        super(message);
    }

    public APIException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
