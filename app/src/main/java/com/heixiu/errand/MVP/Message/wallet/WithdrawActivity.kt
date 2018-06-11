package com.heixiu.errand.MVP.Message.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.QueryMyIncomeBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_withdraw.*
import java.math.BigDecimal

class WithdrawActivity : BaseActivity() {
    var queryMyIncomeBean: QueryMyIncomeBean? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_withdraw)
        initTitle("提现", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        queryMyIncomeBean = intent.getSerializableExtra("data") as QueryMyIncomeBean
        withdraw_tailnumber.text = queryMyIncomeBean!!.zfbId
        withdraw_allmoney.text = queryMyIncomeBean!!.amountAll.toString()
        withdraw_allget.setOnClickListener {
            withdrwa_intomoeny.setText(queryMyIncomeBean!!.amountAll.toString())
        }

    }

    override fun findViewById() {
        withdraw_doit.setOnClickListener {
            if((withdrwa_intomoeny.text) as Long>queryMyIncomeBean!!.amountAll){
                ToastUtils.showLong("提取金额大于拥有金额！")
            }else {
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().withdrawCash(SPUtil.getString("userid"),withdrwa_intomoeny.text as BigDecimal)).subscribe({
                    ToastUtils.showLong(it)
                    startActivity(WithdrawalSuccessActivity::class.java)
                    finishWithAlpha()
                },{
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
