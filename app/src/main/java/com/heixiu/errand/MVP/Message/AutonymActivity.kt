package com.heixiu.errand.MVP.Message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity

class AutonymActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_autonym)
    }

    override fun findViewById() {
        initTitle("身份认证", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })

//        autonym_inname
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}