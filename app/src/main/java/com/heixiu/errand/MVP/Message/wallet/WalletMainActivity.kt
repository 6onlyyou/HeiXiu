package com.heixiu.errand.MVP.Message.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_wallet_main.*

class WalletMainActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_wallet_main)
        initTitle("钱包", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun findViewById() {
        wallet_earn.setOnClickListener {
            startActivity(EarningsActivity::class.java)
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }
}