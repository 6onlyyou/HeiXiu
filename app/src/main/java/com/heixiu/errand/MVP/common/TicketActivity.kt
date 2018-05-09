package com.heixiu.errand.MVP.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.adapter.TicketAdapter
import com.heixiu.errand.bean.CouponTicketBean
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActivity : AppCompatActivity() {


    companion object {
        fun startSelf(context: Context) {
            context.startActivity(Intent(context, TicketActivity::class.java))
        }
    }

    lateinit var adapter: TicketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        back.setOnClickListener({ finish() })
        ticketRv.layoutManager = LinearLayoutManager(this)
        var arrayList = ArrayList<CouponTicketBean>()
        arrayList.add(CouponTicketBean())

        adapter = TicketAdapter(arrayList)
        ticketRv.adapter = adapter

    }
}
