package com.heixiu.errand.MyApplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.fushuaige.common.utils.Utils;
import com.heixiu.errand.net.AndroidBase;
import com.heixiu.errand.utils.SPUtil;
import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_ALIASW = 1002;
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
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS));

            /**
             *这里设置了别名，在这里获取的用户登录的信息
             *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
             **/
            //false状态为未设置标签与别名成功
            //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {

            // }
        }else{
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIASW));

        }
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("TAG", "Set alias in handler.");
                    Set<String> tags = new HashSet<String>();
                    //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
                    tags.add(SPUtil.getString("userid"));//设置tag
                    //上下文、别名【Sting行】、标签【Set型】、回调
                    JPushInterface.setAliasAndTags(getApplicationContext(), SPUtil.getString("userid"), tags,
                            mAliasCallback);
                    break;
                case MSG_SET_ALIASW:
                    Log.d("TAG", "Set alias in handler.");
                    Set<String> tags1 = new HashSet<String>();
                    JPushInterface.setAliasAndTags(getApplicationContext(), "", tags1, mAliasCallback);
                    break;
                default:
                    Log.i("TAG", "Unhandled msg - " + msg.what);
            }
        }
    };
    public void setAlisa() {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIASW));
    }
    public void setGetAlisa() {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS));
    }
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };
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
