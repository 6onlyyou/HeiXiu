package com.heixiu.errand.MVP.Seting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.MVP.Message.AliBindActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_ali_bind_pay.*

class AliPayBindActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_ali_bind_pay)
    }

    override fun findViewById() {
        initTitle("支付宝", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun setListener() {
        setchanger.setOnClickListener {
            startActivity(AliBindActivity::class.java)
        }
        bind_zfb.setText("已绑定支付宝账号:"+SPUtil.getString("bindzfb"))
    }

    override fun processLogic() {
    }

}
