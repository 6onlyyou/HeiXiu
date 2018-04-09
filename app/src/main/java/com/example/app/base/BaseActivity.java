package com.example.app.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.app.MyApplication.MyApplication;


public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private ConnectivityManager manager;
    boolean isNeedFullBar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isNeedFullBar) {
            setFullBar();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        // setTheme(R.style.AnimationActivity);//设置切换动画
//		mContext = getActivityContext();
        initView();
        initdata();
        MyApplication.getInstance().addActivity(this);
    }

    private void setFullBar() {
        Window window = this.getWindow();
        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        //首先使 ChildView 不预留空间
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
        }
        int statusBarHeight = 10;
        //需要设置这个 flag 才能设置状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //避免多次调用该方法时,多次移除了 View
        if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
            //移除假的 View.
            mContentView.removeView(mChildView);
            mChildView = mContentView.getChildAt(0);
        }
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //清除 ChildView 的 marginTop 属性
            if (lp != null && lp.topMargin >= statusBarHeight) {
                lp.topMargin -= statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
    }

    /**
     * 初始activity方法
     */
    private void initView() {
        loadViewLayout();
    }

    private void initdata() {
        findViewById();
        setListener();
        processLogic();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		StatService.onPause(mContext);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//		StatService.onResume(mContext);
    }

    public void checkNetWork() {

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        MyApplication.getInstance().finishActivity(this);
    }

    /**
     * 加载页面layout
     */
    protected abstract void loadViewLayout();

    /**
     * 加载页面元素
     */
    protected abstract void findViewById();

    /**
     * 设置各种事件的监听器
     */
    protected abstract void setListener();

    /**
     * 业务逻辑处理，主要与后端交互
     */
    protected abstract void processLogic();


    /**
     * Activity.this
     */
//	protected abstract Context getActivityContext();

    /**
     * 弹出Toast
     *
     * @param text
     */
    public void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    //字体适配http://blog.csdn.net/luomusha/article/details/41245591

    private int changeTextSize(int size) {
        Resources r = getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                size, r.getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度(px)
     *
     * @param
     * @return
     */
    public int getMobileWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
    }

    /**
     * 获取屏幕高度(px)
     *
     * @param
     * @return
     */
    public int getMobileHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        return height;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }
}
