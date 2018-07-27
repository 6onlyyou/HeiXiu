package com.heixiu.errand.MVP.Message.myorder

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.InfoWindow
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.fushuaige.common.utils.GlideUtil
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Seting.DescriptionActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_order_sending.*
import java.util.*


class OrderSendingActivity : BaseActivity() {
    companion object {
        fun startSelf(context: Context, orderInfo: OrderInfo) {
            val intent = Intent(context, OrderSendingActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }

    override fun processLogic() {

    }

    override fun setListener() {
    }

    override fun loadViewLayout() {

        setContentView(R.layout.activity_order_sending)
    }

    lateinit var mBaiduMap: BaiduMap
    lateinit var orderInfo: OrderInfo
    override fun findViewById() {

        initTitle("我的订单", R.color.colorPrimary, R.color.white)

        mBaiduMap = map.map

        orderInfo = intent.getSerializableExtra("data") as OrderInfo
        orderNo.text = "订单编号: " + orderInfo.orderNum
        order_feedback.setOnClickListener {
            startActivity(DescriptionActivity::class.java, orderInfo.orderNum)
        }
        message.setOnClickListener {
            RongIM.getInstance().setMessageAttachedUserInfo(true)
            RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString("userid"), SPUtil.getString("nickname"), Uri.parse(SPUtil.getString("headurl").toString())))
            RongIM.getInstance().startPrivateChat(this,  orderInfo.recieveUserInfo.userId, orderInfo.recieveUserInfo.nickName)
        }

        order_finish.setOnClickListener({
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().changeOrderStatus(orderInfo.orderNum, "", "4"))
                    .subscribe({
                        ToastUtils.showLong("该订单已确认完成")
                        finish()
                    }, {
                        ToastUtils.showLong("确认完成失败" + it.message)
                    })
        })

        call.setOnClickListener({
            if (receverInfo != null)
                requestPression()
        })

        initUpdateLocation()
    }

    fun requestPression() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

                Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show()
                // 帮跳转到该应用的设置界面，让用户手动授权
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri1 = Uri.fromParts("package", packageName, null)
                intent.data = uri1
                startActivity(intent)
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CALL_PHONE), 1)
            }
        } else {
            // 已经获得授权，可以打电话
            val uri = Uri.parse("tel:" + receverInfo?.userId)
            val intent2 = Intent(Intent.ACTION_CALL, uri)
            startActivity(intent2)
        }
    }


    private var receverInfo: OrderInfo.RecieveUserInfoBean? = null

    override fun onResume() {
        super.onResume()
        map.onResume()

    }

    var timer: Timer = Timer()

    private fun initUpdateLocation() {
        var task: TimerTask = object : TimerTask() {
            override fun run() {
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryOneOrderInfo(orderInfo.orderNum))
                        .subscribe({
                            orderInfo = it
                            receverInfo = it.recieveUserInfo
                            if ("0" == orderInfo.orderStatus || "4" == orderInfo.orderStatus) {
                                // 刚创建 或者 已经完成
                                OrderFinishActivity.startSelf(this@OrderSendingActivity, orderInfo)
                                finish()
                            } else {
                                // 需要显示骑手位置
                                if (it.recieveUserInfo != null) {
                                    GlideUtil.load(this@OrderSendingActivity, it.recieveUserInfo.userImg, sendAva)
                                    Log.i("经纬度", "订单号 ： " + orderInfo.orderNum + " 快递经纬度：" + it.recieveUserInfo.recieveOriginsLatitude + "  " + it.recieveUserInfo.recieveOriginsLongitude)
                                    showMarker(it.recieveUserInfo.recieveOriginsLatitude, it.recieveUserInfo.recieveOriginsLongitude)
                                    name.text = it.recieveUserInfo.nickName + ""
                                }
                            }
                        }, {

                        })
            }
        }
        timer.schedule(task, 0, 20000)
    }

    private fun showMarker(lat: Double, long: Double) {

        //定义用于显示该InfoWindow的坐标点
        val pt = LatLng(lat, long)
        var view = layoutInflater.inflate(R.layout.maker_position, null)


        var distance = DistanceUtil.getDistance(
                LatLng(lat, long),
                LatLng(orderInfo.destinationsLatitude, orderInfo.destinationsLongitude)) /*/ 1000*/

        view.findViewById<TextView>(R.id.distance).text = "距您" + distance.toString().subSequence(0, distance.toString().indexOf(".")) + "米"

        val mInfoWindow = InfoWindow(view, pt, 0)
        mBaiduMap.showInfoWindow(mInfoWindow)

//        var point: LatLng = LatLng(30.963175, 116.400244)
//
//        var bitmap: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_receiver_location)
//
//        var option: OverlayOptions = MarkerOptions()
//                .position(point)
//                .icon(bitmap)
//
//        mBaiduMap.addOverlay(option)

        val mMapStatus = MapStatus.Builder()
                .target(pt)
                .zoom(13f)
                .build()  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        mBaiduMap.setMapStatus(mMapStatusUpdate)
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
        timer.cancel()
    }
}
