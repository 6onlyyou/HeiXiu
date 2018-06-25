package com.heixiu.errand.MVP.Seting

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.CountDownTimerUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_change_phone.*

class ChangePhoneActivity : BaseActivity() {
    var mCountDownTimerUtils: CountDownTimerUtils? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_change_phone)
    }

    override fun findViewById() {
        initTitle("重置手机号", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        change_phone.text = "重置手机号需要验证绑定的手机号"+ SPUtil.getString("userid")
    }
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                ToastUtils.showLong("验证码错误")
            } else {
                ToastUtils.showLong(msg.obj.toString())
            }
        }
    }
    override fun setListener() {
        mCountDownTimerUtils = CountDownTimerUtils(Tv_getCode, 60000, 1000)
        Tv_getCode.setOnClickListener {
            if (SPUtil.getString("userid").equals("")) {
                ToastUtils.showLong("电话号不能为空")
            } else {
                sendCode("86", SPUtil.getString("userid"))
            }
        }
        change_submit.setOnClickListener {
            submitCode("86", SPUtil.getString("userid"),Tv_inCode.text.toString());
        }
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    fun sendCode(country: String, phone: String) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    mCountDownTimerUtils!!.start()
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
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
//                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().passwordSetting(SPUtil.getString("userid"), change_phones.text.toString())).subscribe({
//                        ToastUtils.showLong("修改成功")
//                        finishWithAlpha()
//                    }, {
//                        ToastUtils.showLong(it.message)
//                    })
                    val intent = Intent(this@ChangePhoneActivity,ChangeConfirmPhoneActivity::class.java)
                    intent.putExtra("bindphone", change_phones.text.toString())
                   startActivity(intent)
                    finishWithAlpha()
                } else {
                    handler.sendEmptyMessage(1)
                }
            }
        })
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code)
    }
    override fun processLogic() {
    }
}