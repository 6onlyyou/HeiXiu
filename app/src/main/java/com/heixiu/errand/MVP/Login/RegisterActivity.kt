package com.heixiu.errand.MVP.Login

import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MainActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.ApiService
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.CountDownTimerUtils
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_phone_login.*

class RegisterActivity : BaseActivity() {
    var mCountDownTimerUtils: CountDownTimerUtils? =null
    var handler: Handler = object : Handler() {
       override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
           if(msg.what==1) {
               ToastUtils.showLong("验证码错误")
           }else{
               ToastUtils.showLong(msg.obj.toString())
           }
        }
    }
    override fun loadViewLayout() {
        setContentView(R.layout.activity_register)
        initTitle("注册", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
    }

    override fun findViewById() {
        mCountDownTimerUtils = CountDownTimerUtils(Tv_getCode, 60000, 1000)
        Tv_getCode.setOnClickListener{
            if (Et_inPhone.text.toString().equals("")){
                ToastUtils.showLong("电话号不能为空");
            }else {
                sendCode("86", Et_inPhone.text.toString())
            }
        }
        Bt_signIn.setOnClickListener {
//            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().register(Et_inPhone.text.toString(),Et_inUsername.text.toString(),Et_inPass.text.toString())).subscribe({
//                var message:Message = Message()
//                message.obj ="注册成功"
//                handler.sendMessage(message);
//                startActivity(LoginActivity::class.java)
//            },{
//                var message:Message = Message()
//                message.obj = it.message
//                handler.sendMessage(message);
//            })
            if(Et_inPass.length()<6){
                ToastUtils.showLong("密码太短");
                return@setOnClickListener
            }
            if(Et_inPass.text.toString().equals("")||Et_inPhone.text.toString().equals("")||Tv_inCode.text.toString().equals("")){
                ToastUtils.showLong("不能为空");
            }else {
                submitCode("86", Et_inPhone.text.toString(), Tv_inCode.text.toString())
            }
        }
    }

    override fun setListener() {

    }

    override fun processLogic() {

    }
    fun sendCode(country: String, phone: String) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
            override  fun afterEvent(event: Int, result: Int, data: Any) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    mCountDownTimerUtils!!.start()
                } else {
                    handler.sendEmptyMessage(1);
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
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().register(phone,phone,Et_inPass.text.toString())).subscribe({
                        var message:Message = Message()
                        message.obj = "注册成功"
                        handler.sendMessage(message);
                        finishWithAlpha()
                    },{
                        var message:Message = Message()
                        message.obj = it.message
                        handler.sendMessage(message);
                    })

                } else {
                    handler.sendEmptyMessage(1);
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
}
