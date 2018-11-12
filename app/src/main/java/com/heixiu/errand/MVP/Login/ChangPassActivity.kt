package com.heixiu.errand.MVP.Login

import android.os.Handler
import android.os.Message
import android.view.View
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MainActivity
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.CountDownTimerUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_changpass.*
import java.io.UnsupportedEncodingException
import java.util.*

class ChangPassActivity : BaseActivity() {
    var mCountDownTimerUtils: CountDownTimerUtils? =null
    var handler: Handler = object : Handler() {
       override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
           if(msg.what==1) {
               ToastUtils.showLong("验证码错误")
           }else if(msg.what==2){
               RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().loginByPhone(Et_inPhone.text.toString(), SPUtil.getString("city").toString())).subscribe({
                   ToastUtils.showLong("登入成功")
                   SPUtil.saveString("token", it.token)
                   SPUtil.saveString("userid", Et_inPhone.text.toString())
                   SPUtil.saveString("rongyun_token", it.rongyun_token)
                   MyApplication.getInstance().setGetAlisa()
                   startActivity(MainActivity::class.java)
                   finishWithAlpha()
               }, {
                   ToastUtils.showLong(it.message)
               })
           }
        }
    }
    override fun loadViewLayout() {
        setContentView(R.layout.activity_changpass)
        initTitle("忘记密码", R.color.colorPrimary, R.color.white)
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
            override fun afterEvent(event: Int, result: Int, data: Any) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        mCountDownTimerUtils!!.start()
                        // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    }
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
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().passwordSetting( phone, Et_inPass.text.toString()))
                                .subscribe({
                                    var message: Message = Message()
                                    message.what = 2
                                    message.obj = "找回密码成功"
                                    handler.sendMessage(message);
                                }, {
                                    ToastUtils.showLong(it.message)
                                })
                    }
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