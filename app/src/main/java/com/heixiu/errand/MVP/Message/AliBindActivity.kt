package com.heixiu.errand.MVP.Message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_ali_bind.*

class AliBindActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_ali_bind)
    }

    override fun findViewById() {
        initTitle("支付宝绑定", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })
        bind_ali.setOnClickListener {
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().bindZfb(SPUtil.getString("userid"),autonym_inname.text.toString(),bind_name.text.toString())).subscribe({
                ToastUtils.showLong("保存成功")
                finishWithAlpha()
            },{
                ToastUtils.showLong(it.message)
            })
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
