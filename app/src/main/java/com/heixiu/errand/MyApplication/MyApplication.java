package com.heixiu.errand.MyApplication;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.fushuaige.common.utils.Utils;
import com.heixiu.errand.net.AndroidBase;
import com.heixiu.errand.utils.SPUtil;
import com.mob.MobSDK;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import okhttp3.MediaType;
import okhttp3.RequestBody;



/*
 *自定义Application
 * 用于初始化各种数据以及服务
 *  */

public class MyApplication extends MultiDexApplication {
    /**
     * 应用实例
     **/
    private static MyApplication instance;

    public double localLat = 0.0;

    public double localLong = 0.0;
    public String city = "";
    //记录当前栈里所有activity
    private List<Activity> activities = new ArrayList<Activity>();
    //记录需要一次性关闭的页面
    private List<Activity> activitys = new ArrayList<Activity>();

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    public static Map<String, RequestBody> getp(ArrayList<File> fileList) {
        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileList.get(i));
            paramsMap.put("file\";filename=\"" + fileList.get(i).getName(), fileBody);
        }
        return paramsMap;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RongIM.init(this);
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        MobSDK.init(this);
        AndroidBase.init(this, "http://app.heixiuapp.cn/api/", "http://app.heixiuapp.cn/api/");
        Utils.init(this);
        ZXingLibrary.initDisplayOpinion(this);
        SDKInitializer.initialize(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if (!TextUtils.isEmpty(SPUtil.getString("userid"))) {
            JPushInterface.setAlias(this, 1, SPUtil.getString("userid"));
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    *给临时Activitys
    * 添加activity
    * */
    public void addTemActivity(Activity activity) {
        activitys.add(activity);
    }

    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    * 退出指定的Activity
    * */
    public void exitActivitys() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
