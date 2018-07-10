package com.heixiu.errand.MVP.Message.myorder

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.baidu.mapapi.map.BaiduMap
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
        mBaiduMap = map.map

        orderInfo = intent.getSerializableExtra("data") as OrderInfo
        orderNo.text = orderInfo.orderNum
        name.setText(orderInfo.receiveName)
        order_feedback.setOnClickListener {
            startActivity(DescriptionActivity::class.java, orderInfo.orderNum)
        }
        message.setOnClickListener {
            RongIM.getInstance().setMessageAttachedUserInfo(true)
            RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString("userid"), SPUtil.getString("nickname"), Uri.parse(SPUtil.getString("headurl").toString())))
            RongIM.getInstance().startPrivateChat(this, "18757161476", orderInfo!!.receiveName)
        }

        order_finish.setOnClickListener({

            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().changeOrderStatus(orderInfo.orderNum, orderInfo.receiveId.toString(), "4"))
                    .subscribe({
                        ToastUtils.showLong("该订单已完成")
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
                // 返回值：
                //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show()
                // 帮跳转到该应用的设置界面，让用户手动授权
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri1 = Uri.fromParts("package", packageName, null)
                intent.setData(uri1)
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
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryOneOrderInfo(orderInfo?.orderNum))
                        .subscribe({
                            orderInfo = it
                            receverInfo = it.recieveUserInfo
                            if ("0" == orderInfo.orderStatus || "4" == orderInfo.orderStatus) {
                                // 刚创建 或者 已经完成

                            } else {
                                // 需要显示骑手位置
                                GlideUtil.load(this@OrderSendingActivity, it.recieveUserInfo.userImg, sendAva)

                            }
                        }, {

                        })
            }

        }

        timer.schedule(task, 0, 20000)
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
