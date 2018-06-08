package com.heixiu.errand.MVP.Message.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity

class WithdrawalSuccessActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_withdrawal_success)
        initTitle("申请成功", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
