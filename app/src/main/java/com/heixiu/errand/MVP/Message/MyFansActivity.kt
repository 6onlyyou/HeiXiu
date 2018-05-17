package com.heixiu.errand.MVP.Message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity

class MyFansActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_my_fans)
        initTitle("我的粉丝", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })
    }

    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
