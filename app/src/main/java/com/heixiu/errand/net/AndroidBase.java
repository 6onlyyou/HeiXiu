package com.heixiu.errand.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;


/**
 * Created by YuanGang on 2018/4/12.
 */

public class AndroidBase {

    private static final String TAG = "AndroidBase";
    @NotNull
    public static String BASE_HTTPS_URL;
    @NotNull
    public static String BASE_HTTP_URL;
    public static Context mContext;

    public AndroidBase() {
    }

    public static void init(Context context, String baseHttpUrl, String baseHttpsUrl) {
        String errorMessage = checkStatus();
        if (!TextUtils.isEmpty(errorMessage)) {
                    Log.e(TAG,errorMessage);
        }
        mContext = context;
        BASE_HTTP_URL = baseHttpUrl;
        BASE_HTTPS_URL = baseHttpsUrl;
        OkHttpFactory.INSTANCE.init(context);
        RetrofitFactory.INSTANCE.init();
    }

    private static String checkStatus() {
        String result = "";
        if (mContext == null) {
            result = "context is null";
        } else if (TextUtils.isEmpty(BASE_HTTP_URL)) {
            result = "http path is null";
        } else if (TextUtils.isEmpty(BASE_HTTPS_URL)) {
            result = "https path is null";
        }

        return result;
    }
}
