package com.heixiu.errand.MVP.Express

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.ExpressHistoryAdapter
import com.heixiu.errand.bean.ExpressHistoryBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_express_history.*

class ExpressHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express_history)

        findViewById<ImageView>(R.id.back)!!.setOnClickListener({ finish() })
        historyRv.layoutManager = LinearLayoutManager(this)
        var adapter = ExpressHistoryAdapter(ArrayList<ExpressHistoryBean.LogisticsRecordsBean>())
        historyRv.adapter = adapter

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryLogisticalHistroy(SPUtil.getString("userid")))
                .subscribe({
                    adapter.setNewData(it.logisticsRecords)
                }, {
                    ToastUtils.showShort(it.message + "")
                })
    }
}
