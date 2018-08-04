package com.heixiu.errand.MVP.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.R
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.activity_detail.*

class OrderDetailActivity : AppCompatActivity() {

    companion object {
        fun startSelf(context: Context, orderInfo: OrderInfo) {
            var intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "接单大厅"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        diverLayout.visibility = View.GONE

        var orderInfo = intent.getSerializableExtra("data") as OrderInfo

        orderNo.text = "订单编号： " + orderInfo.orderNum
        start.text = orderInfo.receiveAddress + orderInfo.recieveMapAdress
        end.text = orderInfo.sendAddress + orderInfo.sendMapAdress
        time.text = orderInfo.sendTime
        type.text = orderInfo.name
        weight.text = orderInfo.weight.toString() + "斤"
        add_money.text = orderInfo.addPrice.toString() + "元"
        price.text = "总价" + orderInfo.orderPay +"元"
        tips.text = orderInfo.description
        recipientsame.text = orderInfo.receiveName
        recipientsNum.text = orderInfo.receiveNum
        courierNum.text = orderInfo.courierNum

        backDetail.setOnClickListener({
            finish()
        })
        confirmOrder.setOnClickListener {
            if (TextUtils.isEmpty(SPUtil.getString("userid"))) {
                startActivity(Intent(this@OrderDetailActivity, LoginActivity::class.java))
            } else {
                takeOrder(orderInfo)
            }

        }
    }

    fun takeOrder(orderInfo: OrderInfo) {

        if (SPUtil.getString("userid").equals(orderInfo.userId)) {
            ToastUtils.showShort("不能接自己发的订单呦~")
            return
        }

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().changeOrderStatus(orderInfo.orderNum, SPUtil.getString("userid"), "1"))
                .subscribe({
                    StartOrderDetailActivity.startSelf(this@OrderDetailActivity, orderInfo)
                    finish()
                }, {
                    ToastUtils.showShort(it.message)
                })
    }
}
