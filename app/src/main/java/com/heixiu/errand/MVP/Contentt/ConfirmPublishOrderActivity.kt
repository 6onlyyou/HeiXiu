package com.heixiu.errand.MVP.Contentt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.heixiu.errand.R
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
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
        start.text = orderInfo.sendAddress
        end.text = orderInfo.receiveAddress
        time.text = orderInfo.sendTime
        type.text = orderInfo.name
        weight.text = orderInfo.weight.toString() + "斤"
        add_money.text = orderInfo.addPrice.toString() + "元"
        all_order_price.text = "总价" + orderInfo.payment
        tips.text = orderInfo.description
        recipientsame.text = orderInfo.receiveName
        recipientsNum.text = orderInfo.receiveNum


        back.setOnClickListener({
            finish()
        })

        submitOrder.setOnClickListener({
            submitOrder()
        })
    }

    fun submitOrder() {
        orderInfo.payment = 1100

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
            Log.i("createOrder", it)
        }, {
            Log.i("createOrder", it.message)
        })
    }
}
