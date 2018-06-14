package com.heixiu.errand.MVP.Seting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_account)
    }

    override fun findViewById() {
        initTitle("账号与安全", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun setListener() {
        account_phone.setOnClickListener{

        }
        account_password.setOnClickListener {
            startActivity(ChangePasswordActivity::class.java)
        }
        accoun_approve.setOnClickListener {
            startActivity(AuthenticationActivity::class.java)
        }
        acconnt_alibind.setOnClickListener {
            startActivity(AliPayBindActivity::class.java)
        }
    }

    override fun processLogic() {
    }

}
