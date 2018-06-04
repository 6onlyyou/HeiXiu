package com.heixiu.errand.MVP.Seting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity

class AliPayBindActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_ali_bind_pay)
    }

    override fun findViewById() {
        initTitle("支付宝绑定", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
