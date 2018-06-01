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
import io.reactivex.functions.Consumer
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
        price.text = "总价" + orderInfo.payment
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
        orderInfo.courierNum = "110"
        orderInfo.payment = 1100

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().createOrder(
                "1",
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
                "",
                "",
                "",
                ""
        )).subscribe(Consumer {
            Log.i("createOrder", it)
        }, Consumer {
            Log.i("createOrder", it.message)
        })
    }
}
