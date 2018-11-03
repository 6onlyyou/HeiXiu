package com.heixiu.errand.MVP.Seting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Message.AliBindActivity
import com.heixiu.errand.MVP.Message.AutonymActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
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
            startActivity(ChangePhoneActivity::class.java)
        }
        account_password.setOnClickListener {
            startActivity(ChangePasswordActivity::class.java)
        }
        acconnt_alibind.setOnClickListener {
            if(accoun_bindzfb.text.toString().equals("已认证")){
                startActivity(AliPayBindActivity::class.java)
            }else {
                startActivity(AliBindActivity::class.java)
            }

        }
        accoun_approve.setOnClickListener {
            if(account_certification.text.toString().equals("已认证")){
                startActivity(AuthenticationActivity::class.java)
            }else {
                startActivity(AutonymActivity::class.java)
            }
        }

    }

    override fun processLogic() {

    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryCertificationStatus(SPUtil.getString("userid"))).subscribe({
            account_certification.setText(getstute(it.cardStatus))
            accoun_bindzfb.setText(getstute(it.zfbStatus))
        },{
            ToastUtils.showLong(it.message)
        })
    }
    fun getstute( stute:String):String{
        if(stute.equals("0")){
            return "未认证"
        }
        if(stute.equals("1")){
            return "已认证"
        }
        if(stute.equals("2")){
            return "审核中"
        }
        return "认证失败请重新认证"
    }
}
