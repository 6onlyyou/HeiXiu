package com.heixiu.errand.MVP.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.heixiu.errand.R
import com.heixiu.errand.net.OrderInfo

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
    }
}
