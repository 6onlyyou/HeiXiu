package com.heixiu.errand.MVP.Community

import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity

class PostTextActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_post_text)
        initTitle("图片动态", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        mTitle.setTv_Right("发送",R.color.white, View.OnClickListener { finishWithAnim() })
    }

    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
