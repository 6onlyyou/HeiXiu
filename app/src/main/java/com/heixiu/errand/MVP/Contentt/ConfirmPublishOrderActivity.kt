package com.heixiu.errand.MVP.Contentt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.Event.PublishParamsChangeEvent
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.R
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.RxBus
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_confirm_publish_order.*

class ConfirmPublishOrderActivity : AppCompatActivity() {

    companion object {
        fun startSelf(context: Context, orderInfo: OrderInfo) {
            var intent = Intent(context, ConfirmPublishOrderActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }

    private lateinit var orderInfo: OrderInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_publish_order)

        orderInfo = intent.getSerializableExtra("data") as OrderInfo


//        orderNo.text = "订单编号： " + orderInfo.orderNum
        start.text = orderInfo.receiveAddress
        end.text = orderInfo.sendAddress
        time.text = orderInfo.sendTime
        type.text = orderInfo.name
        weight.text = orderInfo.weight.toString() + "斤"
        add_money.text = orderInfo.addPrice.toString() + "元"
        tips.text = orderInfo.description
        recipientsame.text = "收件人姓名：" + orderInfo.receiveName
        recipientsNum.text = "收件人电话：" + orderInfo.receiveNum

        back.setOnClickListener({
            finish()
        })

        submitOrder.setOnClickListener({
            submitOrder()
        })

        getPrice()
    }

    var canSubmit: Boolean = false

    fun getPrice() {
        var distance = DistanceUtil.getDistance(
                LatLng(orderInfo.destinationsLatitude, orderInfo.destinationsLongitude),
                LatLng(orderInfo.originsLatitude, orderInfo.originsLongitude)) / 1000
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().calculatePrice(orderInfo.weight.toString(), distance.toString()))
                .subscribe({
                    all_order_price.text = it.price.toString() + "元"
                    if (ContentFragment.ticketBean != null) {
                        var realPrice = it.price - ContentFragment.ticketBean?.couponPrice!!
                        if (realPrice < 0) {
                            orderInfo.payment = 0
                            payment.text = "实际付款金额 : 0元"
                        } else {
                            orderInfo.payment = realPrice
                            payment.text = "实际付款金额" + realPrice + "元"
                        }
                        ticket.text = "优惠券金额：" + ContentFragment.ticketBean?.couponPrice!!.toString() + "元"
                    } else {
                        orderInfo.payment = it.price
                        payment.text = "实际付款金额" + it.price + "元"
                        ticket.text = "无优惠券"
                    }
                    canSubmit = true
                }, {
                    canSubmit = false
                    ToastUtils.showShort("订单价格计算失败" + it.message)
                })
    }

    fun submitOrder() {

        if (!canSubmit) {
            ToastUtils.showShort("订单价格计算失败，请重新选择地点")
        }

        if (TextUtils.isEmpty(SPUtil.getString("userid"))) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().createOrder(
                SPUtil.getString("userid"),
                orderInfo.sendAddress,
                orderInfo.receiveAddress,
                orderInfo.name,
                orderInfo.weight,
                orderInfo.addPrice,
                orderInfo.description,
                orderInfo.receiveName,
                orderInfo.receiveNum,
                orderInfo.courierNum,
                orderInfo.supportPrice,
                orderInfo.payment,
                orderInfo.originsLatitude.toString(),
                orderInfo.originsLongitude.toString(),
                orderInfo.destinationsLatitude.toString(),
                orderInfo.destinationsLongitude.toString()
        )).subscribe({
            ToastUtils.showShort("创建订单成功")
            initPublishParams()
            finish()
        }, {
            ToastUtils.showShort(it.message)
        })
    }

    fun initPublishParams() {
        ContentFragment.receiveAddress = ""
        ContentFragment.sendAddress = ""
        ContentFragment.sendTime = ""
        ContentFragment.packageType = ""
        ContentFragment.addPrice = ""
        ContentFragment.receiverName = ""
        ContentFragment.receiverNum = ""
        ContentFragment.packageWeight = ""
        ContentFragment.descriptions = ""
        ContentFragment.courierNum = ""
        ContentFragment.sendLat = 0.0
        ContentFragment.sendLon = 0.0
        ContentFragment.receiveLat = 0.0
        ContentFragment.receiveLon = 0.0
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().post(PublishParamsChangeEvent())
    }
}
