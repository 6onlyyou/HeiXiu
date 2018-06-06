package com.heixiu.errand.MVP.Message.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_earnings.*

class EarningsActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_earnings)
        initTitle("我的收益", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun findViewById() {
        earn_goearn.setOnClickListener {
            startActivity(WithdrawActivity::class.java)
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}