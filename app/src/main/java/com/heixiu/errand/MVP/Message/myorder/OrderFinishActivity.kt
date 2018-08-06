package com.heixiu.errand.MVP.Message.myorder

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.fushuaige.common.utils.GlideUtil
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_order_finish.*

class OrderFinishActivity : BaseActivity() {
    override fun setListener() {

    }

    override fun processLogic() {
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_order_finish)
    }

    lateinit var orderInfo: OrderInfo

    override fun findViewById() {
        orderInfo = intent.getSerializableExtra("data") as OrderInfo
        orderNo.text = "订单编号：" + orderInfo.orderNum
        start.text = orderInfo.receiveAddress + orderInfo.recieveMapAdress
        end.text = orderInfo.sendAddress + orderInfo.sendMapAdress
        time.text = orderInfo.sendTime
        type.text = orderInfo.name
        weight.text = orderInfo.weight.toString() + "斤"
        add_money.text = "加价 " + orderInfo.addPrice + "元"
        tips.text = orderInfo.description
        recipientsame.text = "收件人姓名：" + orderInfo.receiveName
        recipientsNum.text = "收件人电话：" + orderInfo.receiveNum
        price.text = orderInfo.payment.toString() + "元"
//        receiveName.text = orderInfo.receiveName


        when (orderInfo.orderStatus) {
            "0" -> {
                orderState.text = "刚创建"
                cancelOrder.visibility = View.VISIBLE
            }
            "1" -> {
                orderState.text = "已接单"
                cancelOrder.visibility = View.GONE
            }
            "2" -> {
                orderState.text = "已取货"
                cancelOrder.visibility = View.GONE
            }
            "3" -> {
                orderState.text = "已送达"
                cancelOrder.visibility = View.GONE
            }
            "4" -> {
                orderState.text = "完成"
                cancelOrder.visibility = View.GONE
            }
        }

        call.setOnClickListener({
            requestPression()
        })

        cancelOrder.setOnClickListener({
            cancelOrder()
        })


        message.setOnClickListener({
            RongIM.getInstance().setMessageAttachedUserInfo(true)
            RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString("userid"), SPUtil.getString("nickname"), Uri.parse(SPUtil.getString("headurl").toString())))
            RongIM.getInstance().startPrivateChat(this, orderInfo.recieveUserInfo.userId, orderInfo.recieveUserInfo.nickName)
        })
    }

    private var receverInfo: OrderInfo.RecieveUserInfoBean? = null

    fun cancelOrder() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().cancleOrder(orderInfo.orderNum))
                .subscribe({
                    ToastUtils.showLong("取消订单成功")
                    finish()
                }, {
                    ToastUtils.showLong("取消订单失败" + it.message)
                })
    }

    fun requestPression() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

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

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryOneOrderInfo(orderInfo.orderNum))
                .subscribe({
                    orderInfo = it
                    if (it.recieveUserInfo != null) {
                        receiverInfo.visibility = View.VISIBLE
                        courierNum.text = "快递员电话：" + it.recieveUserInfo.userId
                        GlideUtil.load(this@OrderFinishActivity, it.recieveUserInfo.userImg + "", sendAva)
                        receiveName.text = it.recieveUserInfo.nickName
                    } else {
                        receiverInfo.visibility = View.GONE
                    }

                    if (it.userId != SPUtil.getString("userid")) {
                        cancelOrder.visibility = View.GONE
                    }
                }, {

                })
    }

    companion object {
        fun startSelf(context: Context, orderInfo: OrderInfo) {
            var intent = Intent(context, OrderFinishActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }

}
