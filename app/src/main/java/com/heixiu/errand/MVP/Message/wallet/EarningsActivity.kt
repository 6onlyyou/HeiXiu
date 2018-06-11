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
import kotlinx.android.synthetic.main.activity_earnings.*

class EarningsActivity : BaseActivity() {
    var bindStute:Int = 0
  var   queryMyIncomeBean: QueryMyIncomeBean? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_earnings)
        initTitle("我的收益", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })


    }

    override fun findViewById() {
        earn_goearn.setOnClickListener {
            if(bindStute==1) {
                startActivity(WithdrawActivity::class.java,queryMyIncomeBean)
            }
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryMyIncome(SPUtil.getString("userid"))).subscribe({
            queryMyIncomeBean = it
            earn_menoy.text = it.amountAll.toString()
            if(it.zfbStatus==1){
                bindStute = 1
            }
        },{
            ToastUtils.showLong(it.message)
        })
    }
}
