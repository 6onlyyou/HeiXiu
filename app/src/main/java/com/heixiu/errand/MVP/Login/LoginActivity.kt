package com.heixiu.errand.MVP.Login

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.view.View
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.entity.MessageEvent
import com.heixiu.errand.MVP.Login.fragment.AccountLoginFragment
import com.heixiu.errand.MVP.Login.fragment.PhoneLoginFragment
import com.heixiu.errand.R
import com.heixiu.errand.adapter.LoginFragmentAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.listener.MyLocationListener
import com.heixiu.errand.utils.SPUtil
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class LoginActivity : BaseActivity() {
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }

    private var lm: LocationManager? = null//【位置管理】
    var mLocationClient: LocationClient? = null
    var myListener: BDLocationListener = MyLocationListener()
    var adapter: LoginFragmentAdapter? = null;
    var fragments: ArrayList<android.support.v4.app.Fragment>? = ArrayList();
    override fun loadViewLayout() {
        setContentView(R.layout.activity_login)
        EventBus.getDefault().register(this);
        initTitle("登入", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        val fragment1 = PhoneLoginFragment()
        val fragment2 = AccountLoginFragment()
        fragments!!.add(fragment1)
        fragments!!.add(fragment2)
        adapter = LoginFragmentAdapter(supportFragmentManager, fragments)
        viewpager.setAdapter(adapter)
        tablayout.setupWithViewPager(viewpager)
        getPermissions()
    }

    fun getPermissions() {
        var falg = 0
        val rxPermissions = RxPermissions(this)
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECEIVE_SMS)
                .subscribe(object : Consumer<Permission> {
                    @Throws(Exception::class)
                    override fun accept(permission: Permission) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            if (falg == 0) {
                                startLocate()
                            }
                            falg++
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            val builder = AlertDialog.Builder(this@LoginActivity)
                            builder.setTitle("通知")
                                    .setMessage("您尚未开启通知权限，获取不到地址和接收短信服务不能得到服务请开启。")
                                    .setPositiveButton("去开启") { dialog, id ->
                                        getPermissions()
                                    }
                                    .setNegativeButton("取消") { dialog, id -> }
                            builder.create().show()
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
//                        ToastUtils.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用")
//                            getAppDetailSettingIntent(this@LoginActivity)
                        }
                    }
                })
    }

    override fun findViewById() {

    }

    override fun setListener() {

    }

    override fun processLogic() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun messageEventBus(event: MessageEvent) {

        SPUtil.saveString("city", event.city)
        ToastUtils.showLong("" + event.city)
    }

    private fun getAppDetailSettingIntent(context: Context) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName)
        }
        startActivity(localIntent)
    }

    /**
     * 定位
     */
    private fun startLocate() {
        mLocationClient = LocationClient(applicationContext)     //声明LocationClient类
        mLocationClient!!.registerLocationListener(myListener)    //注册监听函数
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Battery_Saving//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll")//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0)//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true)//可选，设置是否需要地址信息，默认不需要
        option.isOpenGps = true//可选，默认false,设置是否使用gps
        option.isLocationNotify = true//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true)//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true)//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false)//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false)//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false)//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient!!.setLocOption(option)
        //开启定位
        mLocationClient!!.start()
    }

}
