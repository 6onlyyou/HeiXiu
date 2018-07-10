package com.heixiu.errand.MVP.Message.wallet

import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.adapter.WithdrawalRecordAdapter
import com.heixiu.errand.adapter.withdrawalAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.WithdrawBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_withdrawal_record.*

class WithdrawalActivity : BaseActivity() {
    var WithdrawBean: List<WithdrawBean>? =null
    var WithdrawBean2: List<WithdrawBean>? =null
  var  withdrawalRecordAdapter: withdrawalAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_withdrawal_record)
        initTitle("提现记录", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        my_doll_rv.setLayoutManager(LinearLayoutManager(mContext))
        val list = ArrayList<WithdrawBean>()
        withdrawalRecordAdapter = withdrawalAdapter(list)
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().withdrawList(SPUtil.getString("userid"))).subscribe({
            WithdrawBean=it
            my_doll_rv.setLayoutManager(LinearLayoutManager(this@WithdrawalActivity))
            my_doll_rv.setAdapter(withdrawalRecordAdapter)
            withdrawalRecordAdapter!!.setNewData(WithdrawBean)
        },{
        })
        dollStateTb.addTab(dollStateTb.newTab().setText("任务交易").setTag("1"))
        dollStateTb.addTab(dollStateTb.newTab().setText("接单交易").setTag("2"))
        dollStateTb.isSelected = true
        dollStateTb.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.tag == "1") {
                    if(WithdrawBean!!.size<1){
                        return
                    }
                    my_doll_rv.setLayoutManager(LinearLayoutManager(this@WithdrawalActivity))
                    my_doll_rv.setAdapter(withdrawalRecordAdapter)
                    withdrawalRecordAdapter!!.setNewData(WithdrawBean)
//                    setSelect(false)
                }
                if (tab.tag == "2") {
                    if(WithdrawBean2!!.size<1){
                        return
                    }
                    my_doll_rv.setLayoutManager(LinearLayoutManager(this@WithdrawalActivity))
                    my_doll_rv.setAdapter(withdrawalRecordAdapter)
                    withdrawalRecordAdapter!!.setNewData(WithdrawBean2)
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