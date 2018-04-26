package com.fushuaige.common.net;

import android.widget.Toast;

import com.fushuaige.common.utils.ToastUtils;
import com.fushuaige.common.utils.Utils;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;

/**
 * Created by Administrator on 2017/11/22.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectTimeoutException) {
            ToastUtils.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof UnknownHostException) {
//            if (Utils.checkNetWork()) {
//                ToastUtils.showShort(e.getMessage());
//            } else {
            Toast.makeText(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
        } else {
            ToastUtils.showShort(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}
