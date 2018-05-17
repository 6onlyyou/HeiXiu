package com.heixiu.errand.MVP.Message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity

class AliBindActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_ali_bind)
    }

    override fun findViewById() {
        initTitle("支付宝绑定", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
