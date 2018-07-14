package com.heixiu.errand.MVP.Express

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.ExpressHistoryAdapter
import com.heixiu.errand.bean.ExpressHistoryBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_express_history.*

class ExpressHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express_history)

        findViewById<ImageView>(R.id.back)!!.setOnClickListener({ finish() })
        historyRv.layoutManager = LinearLayoutManager(this)
        var adapter = ExpressHistoryAdapter(ArrayList<ExpressHistoryBean.LogisticsRecordsBean>())
        historyRv.adapter = adapter
        adapter.setOnRecyclerViewItemClickListener(object : BaseQuickAdapter.OnRecyclerViewItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                getHistory(adapter.getItem(position))
            }
        })

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryLogisticalHistroy(SPUtil.getString("userid")))
                .subscribe({
                    adapter.setNewData(it.logisticsRecords)
                }, {
                    ToastUtils.showShort(it.message + "")
                })
    }

    private fun getHistory(item: ExpressHistoryBean.LogisticsRecordsBean?) {

        var id = ""

        var name = resources.getStringArray(R.array.package_name)
        var ids = resources.getStringArray(R.array.package_id)

        for (i in 0..name.size) {
            if (name[i] == item?.logisticsName) {
                id = ids[i]
                break
            }
        }
        RetrofitFactory.getRetrofit().queryLogisticalInformation(
                id,
                item?.logisticsNum, ""
//                SPUtil.getString("userid")
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        var intent = Intent(this@ExpressHistoryActivity, ExpressMessageActivity::class.java)
                        intent.putExtra("data", it)
                        intent.putExtra("name", item?.logisticsName)
                        startActivity(intent)
                    } else {
                        ToastUtils.showShort("暂无信息")
                    }
                }, {
                    ToastUtils.showShort(it.message)
                })
    }
}
