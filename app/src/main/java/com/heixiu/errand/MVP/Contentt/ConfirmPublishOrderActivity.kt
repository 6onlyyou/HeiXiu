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
import com.heixiu.errand.base.AppConstant
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.bean.PayBean
import com.heixiu.errand.bean.PayFailEventEntity
import com.heixiu.errand.bean.PaySuccessEventEntity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.RxBus
import com.heixiu.errand.utils.SPUtil
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_confirm_publish_order.*
import java.util.*

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
        start.text = orderInfo.receiveAddress + orderInfo.recieveMapAdress
        end.text = orderInfo.sendAddress + orderInfo.sendMapAdress
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

        RxBus.getDefault().toObservable(PaySuccessEventEntity::class.java).subscribe({
            runOnUiThread {
                run {
                    ToastUtils.showLong("支付成功,等待接单")
                    finish()
                }
            }

        }, {
            finish()
        })

        RxBus.getDefault().toObservable(PayFailEventEntity::class.java).subscribe({
            ToastUtils.showLong("支付失败,请重新提交订单")
            finish()

            runOnUiThread {
                ToastUtils.showLong("支付成功,等待接单")
                finish()
            }
        }, {
            finish()
        })
    }

    var canSubmit: Boolean = false

    fun getPrice() {
        var distance = DistanceUtil.getDistance(
                LatLng(orderInfo.destinationsLatitude, orderInfo.destinationsLongitude),
                LatLng(orderInfo.originsLatitude, orderInfo.originsLongitude)) / 1000
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().calculatePrice(orderInfo.weight.toString(), distance.toString()))
                .subscribe({
                    it.price = it.price + orderInfo.addPrice
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
                orderInfo.sendMapAdress,
                orderInfo.receiveAddress,
                orderInfo.recieveMapAdress,
                orderInfo.sendTime,
                orderInfo.name,
                orderInfo.weight,
                orderInfo.addPrice,
                orderInfo.description,
                orderInfo.receiveName,
                orderInfo.receiveNum,
                "12615361526",
                orderInfo.supportPrice,
                orderInfo.payment,
                orderInfo.originsLatitude.toString(),
                orderInfo.originsLongitude.toString(),
                orderInfo.destinationsLatitude.toString(),
                orderInfo.destinationsLongitude.toString()
        )).subscribe({
            ToastUtils.showShort("创建订单成功,获取支付信息")
            wxPay(it)
            orderInfo.orderNum = it.orderNum
            initPublishParams()
//            RxBus.getDefault().post("PublishSuccess")
//            finish()
        }, {
            ToastUtils.showShort(it.message)
        })
    }

    fun wxPay(orderInfo: OrderInfo) {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().createPayOrder("1", orderInfo.orderNum, "1", "192.168.1.1"))
                .subscribe({
                    weChatPay(entity = it)
                }, {
                    ToastUtils.showLong("获取支付信息失败" + it.message)
                })
    }


    override fun onResume() {
        super.onResume()
        if (!TextUtils.isEmpty(orderInfo.orderNum)) {
            var timer: Timer = Timer()

            var task: TimerTask = object : TimerTask() {
                override fun run() {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().getPayResult("1", orderInfo.orderNum))
                            .subscribe({
                                ToastUtils.showLong("支付成功,等待接单")
                                finish()
                            }, {

                            })
                }
            }
            timer.schedule(task, 1000)
        }
    }

    /**
     * 调起微信支付
     */
    private fun weChatPay(entity: PayBean) {
        val api = WXAPIFactory.createWXAPI(this, AppConstant.WX_ID, false)
        api.registerApp(AppConstant.WX_ID)
        val payReq = PayReq()
        payReq.appId = AppConstant.WX_ID
        payReq.partnerId = entity.partnerid
        payReq.prepayId = entity.prepayid
        payReq.packageValue = entity.packageX
        payReq.nonceStr = entity.noncestr
        payReq.timeStamp = entity.timestamp
        payReq.sign = entity.sign
        api.sendReq(payReq)
    }

    fun initPublishParams() {
        ContentFragment.receiveAddress = ""
        ContentFragment.sendAddress = ""
        ContentFragment.sendTime = ""
        ContentFragment.packageType = ""
        ContentFragment.addPrice = "0"
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
