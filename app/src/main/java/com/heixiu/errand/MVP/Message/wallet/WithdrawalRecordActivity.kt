package com.heixiu.errand.MVP.Message.wallet

import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.WithdrawalRecordAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_withdrawal_record.*

class WithdrawalRecordActivity : BaseActivity() {
    var myReciecedOrderBean2: List<OrderInfo> = ArrayList()
    var myReciecedOrderBean: List<OrderInfo> = ArrayList()
    var withdrawalRecordAdapter: WithdrawalRecordAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_withdrawal_record)
        initTitle("交易记录", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        dollStateTb.visibility = View.VISIBLE
        my_doll_rv.layoutManager = LinearLayoutManager(mContext)
        val list = ArrayList<OrderInfo>()
        withdrawalRecordAdapter = WithdrawalRecordAdapter(list)
        my_doll_rv.adapter = withdrawalRecordAdapter

        dollStateTb.addTab(dollStateTb.newTab().setText("任务交易").setTag("1"))
        dollStateTb.addTab(dollStateTb.newTab().setText("接单交易").setTag("2"))
        dollStateTb.getTabAt(0)?.select()
        dollStateTb.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.tag == "1") {
                    if (myReciecedOrderBean.isEmpty()) {
                        withdrawalRecordAdapter!!.setNewData(ArrayList())

                        withdrawalRecordAdapter?.notifyDataSetChanged()
                        return
                    }

                    withdrawalRecordAdapter!!.setNewData(myReciecedOrderBean)
                }
                if (tab.tag == "2") {
                    if (myReciecedOrderBean2.isEmpty()) {
                        withdrawalRecordAdapter!!.setNewData(ArrayList())
                        withdrawalRecordAdapter?.notifyDataSetChanged()
                        return
                    }
                    withdrawalRecordAdapter!!.setNewData(myReciecedOrderBean2)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    override fun findViewById() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryMyTransactionRecords(SPUtil.getString("userid"))).subscribe({

            val orderInfoIterator = it.myPublishOrderInfos.iterator()
            while (orderInfoIterator.hasNext()) {
                val orderInfo = orderInfoIterator.next()
                if (orderInfo.orderStatus == "-1") {
                    orderInfoIterator.remove()
                }
            }

            val myReciecedOrderInfos = it.myReciecedOrderInfos.iterator()
            while (myReciecedOrderInfos.hasNext()) {
                val orderInfo = myReciecedOrderInfos.next()
                if (orderInfo.orderStatus == "-1") {
                    myReciecedOrderInfos.remove()
                }
            }

            myReciecedOrderBean = it.myPublishOrderInfos
            myReciecedOrderBean2 = it.myReciecedOrderInfos
            withdrawalRecordAdapter?.setNewData(myReciecedOrderBean)
        }, {
            ToastUtils.showLong(it.message)
        })
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
