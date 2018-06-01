package com.heixiu.errand.MVP.Seting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_seting_main.*

class SetingMainActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_seting_main)
    }

    override fun findViewById() {
        initTitle("设置", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        setting_account.setOnClickListener {
            startActivity(AccountActivity::class.java)
        }
        setting_notification.setOnClickListener {
            startActivity(SoundActivity::class.java)
        }
//        setting_common.setOnClickListener {
//            startActivity(SoundActivity::class.java)
//        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
