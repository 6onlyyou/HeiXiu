package com.heixiu.errand.MVP.Login.fragment

import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK

import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.utils.CountDownTimerUtils
import kotlinx.android.synthetic.main.fragment_phone_login.*
import cn.smssdk.SMSSDK.RESULT_COMPLETE
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.RegisterActivity
import com.heixiu.errand.MainActivity


class PhoneLoginFragment : BaseFragment() {
    var mCountDownTimerUtils: CountDownTimerUtils? =null

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_phone_login, container, false)
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun initView() {
         mCountDownTimerUtils = CountDownTimerUtils(Tv_code, 60000, 1000)
        Tv_code.setOnClickListener{
            if(Et_phone.text.toString().equals("")){
                ToastUtils.showLong("电话号不能为空")
            }else{
            sendCode("86",Et_phone.text.toString())
            }
        }
        Bt_login.setOnClickListener{
            if(Et_phone.text.toString().equals("")||Et_code.text.toString().equals("")){
                ToastUtils.showLong("账号密码不能为空")
            }else {
                submitCode("86", Et_phone.text.toString(), Et_code.text.toString())
            }
        }
        Tv_sign.setOnClickListener {
            startActivity(RegisterActivity::class.java)
        }
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    fun sendCode(country: String, phone: String) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
          override  fun afterEvent(event: Int, result: Int, data: Any) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    mCountDownTimerUtils!!.start()
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                } else {
                    ToastUtils.showLong("验证码获取错误"+result)
                }
            }
        })
        // 触发操作
        SMSSDK.getVerificationCode(country, phone)
    }
    // 提交验证码，其中的code表示验证码，如“1357”
    fun submitCode(country: String, phone: String, code: String) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    startActivity(MainActivity::class.java)
                } else {
                    ToastUtils.showLong("验证码错误")
                }
            }
        })
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code)
    }
     override fun onDestroy() {
        super.onDestroy()
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler()
    };
}// Required empty public constructor
