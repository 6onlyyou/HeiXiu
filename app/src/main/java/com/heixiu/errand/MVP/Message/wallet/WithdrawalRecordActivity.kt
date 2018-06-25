package com.heixiu.errand.MVP.Message.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.adapter.WithdrawalRecordAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.MyReciecedOrderBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_withdrawal_record.*

class WithdrawalRecordActivity : BaseActivity() {
    var myReciecedOrderBean: List<MyReciecedOrderBean>? =null
    var myReciecedOrderBean2: List<MyReciecedOrderBean>? =null
  var  withdrawalRecordAdapter: WithdrawalRecordAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_withdrawal_record)
        initTitle("交易记录", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        my_doll_rv.setLayoutManager(LinearLayoutManager(mContext))
        val list = ArrayList<MyReciecedOrderBean>()
        withdrawalRecordAdapter = WithdrawalRecordAdapter(list)
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryMyTransactionRecords(SPUtil.getString("userid"))).subscribe({
            myReciecedOrderBean=it.myReciecedOrderInfos
            myReciecedOrderBean2 = it.myPublishOrderInfos
            my_doll_rv.setLayoutManager(LinearLayoutManager(this@WithdrawalRecordActivity))
            my_doll_rv.setAdapter(withdrawalRecordAdapter)
            withdrawalRecordAdapter!!.setNewData(myReciecedOrderBean)
        },{
        })
        dollStateTb.addTab(dollStateTb.newTab().setText("任务交易").setTag("1"))
        dollStateTb.addTab(dollStateTb.newTab().setText("接单交易").setTag("2"))
        dollStateTb.isSelected = true
        dollStateTb.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.tag == "1") {
                    if(myReciecedOrderBean!!.size<1){
                        return
                    }
                    my_doll_rv.setLayoutManager(LinearLayoutManager(this@WithdrawalRecordActivity))
                    my_doll_rv.setAdapter(withdrawalRecordAdapter)
                    withdrawalRecordAdapter!!.setNewData(myReciecedOrderBean)
//                    setSelect(false)
                }
                if (tab.tag == "2") {
                    if(myReciecedOrderBean2!!.size<1){
                        return
                    }
                    my_doll_rv.setLayoutManager(LinearLayoutManager(this@WithdrawalRecordActivity))
                    my_doll_rv.setAdapter(withdrawalRecordAdapter)
                    withdrawalRecordAdapter!!.setNewData(myReciecedOrderBean2)
//                    isSelect = false
//                    setSelect(false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
