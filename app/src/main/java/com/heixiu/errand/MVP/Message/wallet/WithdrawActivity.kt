package com.heixiu.errand.MVP.Message.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_withdraw.*

class WithdrawActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_withdraw)
        initTitle("提现", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun findViewById() {
        withdraw_doit.setOnClickListener {
            startActivity(WithdrawalSuccessActivity::class.java)
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
