package com.heixiu.errand.MVP.Message.wallet

import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.QueryMyIncomeBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_wxwithdraw.*

class WxWithdrawActivity : BaseActivity() {
    var queryMyIncomeBean: QueryMyIncomeBean? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_wxwithdraw)
        initTitle("提现", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        queryMyIncomeBean = intent.getSerializableExtra("data") as QueryMyIncomeBean
//        withdraw_tailnumber.text = queryMyIncomeBean!!.zfbId
        if(queryMyIncomeBean!!.amountAvailable==null){
            withdraw_allmoney.text = "0"
        }else{
            withdraw_allmoney.text = queryMyIncomeBean!!.amountAvailable.toString()
        }

        withdraw_allget.setOnClickListener {
            if(queryMyIncomeBean!!.amountAvailable==null){
                withdrwa_intomoeny.setText("0")
            }else{
                withdrwa_intomoeny.setText(queryMyIncomeBean!!.amountAvailable.toString())
            }
        }
    }

    override fun findViewById() {


        withdraw_doit.setOnClickListener {
            if( withdraw_tailnumber.text.toString().equals("")){
                ToastUtils.showLong("微信号不能为空！")
                return@setOnClickListener
            }
            if(withdrwa_intomoeny.text.toString().equals("")){
                ToastUtils.showLong("请输入提现金额！")
                return@setOnClickListener
            }
            var pieceall:Double = withdrwa_intomoeny.text.toString().toDouble()
            if(pieceall >queryMyIncomeBean!!.amountAvailable.toDouble()){
                ToastUtils.showLong("提取金额大于拥有金额！")
                return@setOnClickListener
            }else {
                withdraw_doit.isEnabled = false
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().withdrawCash(SPUtil.getString("userid"), withdraw_tailnumber.text.toString(), pieceall, "1")).subscribe({
                    ToastUtils.showLong(it)
                    startActivity(WithdrawalActivity::class.java)
//                    startActivity(WithdrawalSuccessActivity::class.java)
                    finishWithAlpha()
                    withdraw_doit.isEnabled = true
                },{
                    withdraw_doit.isEnabled = true
                    ToastUtils.showLong(it.message)
                })
            }
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

    override fun onResume() {
        super.onResume()
    }
}