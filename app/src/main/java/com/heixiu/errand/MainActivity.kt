package com.heixiu.errand

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Community.CommunityFragment
import com.heixiu.errand.MVP.Contentt.ContentFragment
import com.heixiu.errand.MVP.Express.ExpressFragment
import com.heixiu.errand.MVP.Home.HomeFragment
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.MVP.Login.entity.MessageEvent
import com.heixiu.errand.MVP.Message.MessageFragment
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.CouponTicketBean
import com.heixiu.errand.listener.MyLocationListener
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.RxBus
import com.heixiu.errand.utils.SPUtil
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class MainActivity : BaseActivity() {
    private var fragments: ArrayList<android.support.v4.app.Fragment>? = null
    var mLocationClient: LocationClient? = null
    private val myListener = MyLocationListener()
    var stuteLocation = 0

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
        timer.cancel()
    }

    override fun findViewById() {
        EventBus.getDefault().register(this);
//        SPUtil.saveString("userid","15632617141")
//        SPUtil.saveString("city","杭州市")
        Rl_homepass.setOnClickListener({
            Iv_homepass.setImageResource(R.mipmap.homepass)
            Iv_expressnopass.setImageResource(R.mipmap.expressnopass)
            Iv_communitynopass.setImageResource(R.mipmap.communitynopass)
            Iv_messnopass.setImageResource(R.mipmap.messnopass)
            switchFragment(0)
        });
        Rl_expressnopass.setOnClickListener({
            Iv_homepass.setImageResource(R.mipmap.homenopass)
            Iv_expressnopass.setImageResource(R.mipmap.expresspass)
            Iv_communitynopass.setImageResource(R.mipmap.communitynopass)
            Iv_messnopass.setImageResource(R.mipmap.messnopass)
            switchFragment(1)
        });
        Rl_contentt.setOnClickListener({
            Iv_homepass.setImageResource(R.mipmap.homenopass)
            Iv_expressnopass.setImageResource(R.mipmap.expressnopass)
            Iv_communitynopass.setImageResource(R.mipmap.communitynopass)
            Iv_messnopass.setImageResource(R.mipmap.messnopass)
            switchFragment(2)
        });
        Rl_communitynopass.setOnClickListener({
            Iv_homepass.setImageResource(R.mipmap.homenopass)
            Iv_expressnopass.setImageResource(R.mipmap.expressnopass)
            Iv_communitynopass.setImageResource(R.mipmap.communitypass)
            Iv_messnopass.setImageResource(R.mipmap.messnopass)

            switchFragment(3)
        });
        Rl_messnopass.setOnClickListener({
            if (SPUtil.getString("userid").equals("") || SPUtil.getString("userid").equals("1")) {

                startActivity(LoginActivity::class.java)
                return@setOnClickListener
            }
            Iv_homepass.setImageResource(R.mipmap.homenopass)
            Iv_expressnopass.setImageResource(R.mipmap.expressnopass)
            Iv_communitynopass.setImageResource(R.mipmap.communitynopass)
            Iv_messnopass.setImageResource(R.mipmap.messagepass)
            switchFragment(4)
        });

    }

    fun getUserMessage() {
        if (SPUtil.getString("userid").equals("") || SPUtil.getString("userid").equals("1")) {
        } else {
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().selectDataById(SPUtil.getString("userid"))).subscribe({
                if (it.userInfo.userImg == null) {
                    SPUtil.saveString("headurl", "")
                } else {
                    SPUtil.saveString("headurl", it.userInfo.userImg)
                }

                SPUtil.saveString("nickname", it.userInfo.nickName)
                if (it.dbSubAccount?.zfbId == null) {
                    SPUtil.saveString("bindzfb", "0")
                } else {
                    SPUtil.saveString("bindzfb", it.dbSubAccount?.zfbId + "")
                }
            }, {
                ToastUtils.showLong(it.message)
            })
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_main)

        mLocationClient = LocationClient(applicationContext)
        //声明LocationClient类
        mLocationClient?.registerLocationListener(myListener)
        //注册监听函数
        initLocationConfig()
        mLocationClient?.start()
        fragments = ArrayList()
        addFragment()

        switchFragment(0)
        getPermissions()
        RxBus.getDefault().toObservable(CouponTicketBean::class.java).subscribe({


            val fragmentTransaction = supportFragmentManager.beginTransaction()
            for (i in 0 until fragments!!.size) {
                val fragment = fragments!!.get(i)
                if (i == 2) {
//                    if (fragment.isAdded) {
                    fragmentTransaction.show(fragment)
//                    } else {
//                        fragmentTransaction.add(R.id.fl_container, fragment)
//                    }
                } else {
//                    if (fragment.isAdded) {
                    fragmentTransaction.hide(fragment)
//                    }
                }
            }
            fragmentTransaction.commit()
            ContentFragment.ticketBean = it
        }, {

        })

        initUpdateLocation()
    }

    fun initLocationConfig() {
        val option = LocationClientOption()

        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
//        可选，设置定位模式，默认高精度
//        LocationMode.Hight_Accuracy：高精度；
//        LocationMode.Battery_Saving：低功耗；
//        LocationMode.Device_Sensors：仅使用设备；
        option.setIsNeedAddress(true)
        option.setCoorType("bd09ll")
//        可选，设置返回经纬度坐标类型，默认gcj02
//        gcj02：国测局坐标；
//        bd09ll：百度经纬度坐标；
//        bd09：百度墨卡托坐标；
//        海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(10000)
//        可选，设置发起定位请求的间隔，int类型，单位ms
//        如果设置为0，则代表单次定位，即仅定位一次，默认为0
//        如果设置非0，需设置1000ms以上才有效
        option.isOpenGps = true
//        可选，设置是否使用gps，默认false
//        使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.isLocationNotify = true
//        可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false)
//        可选，定位SDK内部是一个service，并放到了独立进程。
//        设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false)
//        可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000)
//        可选，7.2版本新增能力
//        如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false)
//        可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient?.locOption = option
//        mLocationClient为第二步初始化过的LocationClient对象
//        需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//        更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

    /**
     * 创建fragment实例并把他们加入集合
     */
    fun addFragment() {
        fragments!!.add(HomeFragment())
        fragments!!.add(ExpressFragment())
        fragments!!.add(ContentFragment())
        fragments!!.add(CommunityFragment())
        fragments!!.add(MessageFragment())
    }

    override fun onResume() {
        super.onResume()
        getUserMessage()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = java.util.ArrayList<String>()
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO)
            }

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA)
            }

            if (permissions.size != 0) {
                requestPermissionsForM(permissions)
            }
        }
        if (!SPUtil.getString("rongyun_token").equals("")) {
            connect(SPUtil.getString("rongyun_token"))
        }

    }

    var timer: Timer = Timer()
    private fun initUpdateLocation() {

        var task: TimerTask = object : TimerTask() {
            override fun run() {
                if (!TextUtils.isEmpty(SPUtil.getString("userid"))) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryAllMyRecieveOrderInfos(SPUtil.getString("userid")))
                            .subscribe({
                                if (it != null && it.size > 0) {
                                    Log.i("order", "接单数量大于0")
                                    for (orderInfo in it) {
                                        if (orderInfo.orderStatus.equals("1") || orderInfo.orderStatus.equals("2") || orderInfo.orderStatus.equals("3"))
                                            if (MyApplication.getInstance().localLong != 0.0) {
                                                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit()
                                                        .takeOrder(SPUtil.getString("userid"), orderInfo.orderNum, MyApplication.getInstance().localLong, MyApplication.getInstance().localLat))
                                                        .subscribe({
                                                            Log.i("order", "提交位置成功")
                                                        }, {
                                                            Log.i("order", "提交位置失败" + it.message)
                                                        })
                                            }
                                    }
                                }
                            }) {

                            }
                }
            }
        }
        timer.schedule(task, 1000, 20000)
    }

    private fun connect(token: String) {
        RongIM.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onTokenIncorrect() {
                Log.e("LoginActivity", "--onTokenIncorrect")
            }

            override fun onSuccess(userid: String) {
                Log.e("LoginActivity", "--onSuccess--" + userid)

                //服务器连接成功，跳转消息列表
                //
            }

            override fun onError(errorCode: RongIMClient.ErrorCode) {
                Log.e("LoginActivity", "--onError")
            }
        })
    }

    private fun requestPermissionsForM(per: java.util.ArrayList<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(per.toTypedArray(), 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * 点击切换fragment
     *
     * @param position
     */
    fun switchFragment(position: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        for (i in 0 until fragments!!.size) {
            val fragment = fragments!!.get(i)
            if (i == position) {
                if (fragment.isAdded) {
                    fragmentTransaction.show(fragment)
                } else {
                    fragmentTransaction.add(R.id.fl_container, fragment)
                }
            } else {
                if (fragment.isAdded) {
                    fragmentTransaction.hide(fragment)
                }
            }
        }
        //提交事务
        fragmentTransaction.commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun messageEventBus(event: MessageEvent) {
//
        if (event.city.equals("")) {

        } else {
            stuteLocation = 1
            SPUtil.saveString("city", event.city)
            mLocationClient!!.stop()

        }
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
        option.setScanSpan(1000)//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
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
        if (stuteLocation == 0) {
            mLocationClient!!.start()
        }

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
                            val builder = AlertDialog.Builder(this@MainActivity)
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
}
