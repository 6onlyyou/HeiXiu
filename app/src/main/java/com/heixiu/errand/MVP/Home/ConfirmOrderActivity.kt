package com.heixiu.errand.MVP.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.heixiu.errand.R
import com.heixiu.errand.net.OrderInfo
import kotlinx.android.synthetic.main.activity_confirm_order.*

class ConfirmOrderActivity : AppCompatActivity() {

    companion object {
        fun startSelf(context: Context, orderInfo: OrderInfo) {
            var intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)
        var orderInfo = intent.getSerializableExtra("data") as OrderInfo

        orderNo.text = "订单编号： " + orderInfo.orderNum
        start.text = orderInfo.sendAddress
        end.text = orderInfo.receiveAddress
        type.text = orderInfo.name
        weight.text = orderInfo.weight.toString() + "斤"
        add_money.text = orderInfo.addPrice.toString() + "元"
        price.text = "总价" + orderInfo.payment
        tips.text = orderInfo.description
        recipientsame.text = orderInfo.receiveName
        recipientsNum.text = orderInfo.receiveNum
        courierNum.text = orderInfo.courierNum

        back.setOnClickListener({
            finish()
        })
        startOrder.setOnClickListener {
            StartOrderDetailActivity.startSelf(this@ConfirmOrderActivity, orderInfo)
            finish()
        }
    }
}
