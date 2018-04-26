package com.fushuaige.common.net;

import com.fushuaige.common.utils.ToastUtils;
import com.fushuaige.common.utils.Utils;

import org.apache.http.conn.ConnectTimeoutException;
import org.reactivestreams.Subscriber;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/11/17.
 */

public abstract class BaseSubscribe<T> implements Subscriber<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectTimeoutException) {
            ToastUtils.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof UnknownHostException) {
            if (Utils.checkNetWork()) {
                ToastUtils.showShort(e.getMessage());
            } else {
                ToastUtils.showShort("网络中断，请检查您的网络状态");
            }
        } else {
            ToastUtils.showShort(e.getMessage());
        }
    }
}
