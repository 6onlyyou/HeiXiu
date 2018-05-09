package com.heixiu.errand.MVP.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.heixiu.errand.R
import com.heixiu.errand.net.OrderInfo

import kotlinx.android.synthetic.main.activity_start_order_detail.*

class StartOrderDetailActivity : AppCompatActivity() {


    private var orderInfo: OrderInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_order_detail)

        orderInfo = intent.getSerializableExtra("data") as OrderInfo

        back.setOnClickListener(View.OnClickListener { finish() })

        orderNo.text = "订单编号：" + orderInfo!!.orderNum
        start.text = orderInfo!!.sendAddress
        end.text = orderInfo!!.receiveAddress
        time.text = orderInfo!!.sendTime
        add_money.text = "加价" + orderInfo!!.addPrice
        tips.text = orderInfo!!.description
        recipientsame.text = "收件人姓名：" + orderInfo!!.receiveName
        recipientsNum.text = "收件人电话：" + orderInfo!!.receiveNum
        courierNum.text = "快递员电话：" + orderInfo!!.courierNum

        take_order.setOnClickListener(View.OnClickListener {
            // 取到物品
        })

        startMap.setOnClickListener(View.OnClickListener { OrderMapActivity.startSelf(this@StartOrderDetailActivity, orderInfo) })
    }

    companion object {

        fun startSelf(context: Context, orderInfo: OrderInfo) {
            val intent = Intent(context, StartOrderDetailActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }
}
