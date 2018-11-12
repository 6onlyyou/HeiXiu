package com.heixiu.errand.MVP.Login.fragment

import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.ChangPassActivity
import com.heixiu.errand.MVP.Login.RegisterActivity
import com.heixiu.errand.MainActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_account_login.*
import kotlinx.android.synthetic.main.fragment_phone_login.*
import io.rong.imlib.RongIMClient
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.MyApplication.MyApplication
import io.rong.imkit.RongIM
import io.rong.imkit.utils.SystemUtils.getCurProcessName



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountLoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountLoginFragment : BaseFragment() {
    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_account_login, container, false)
    }
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
    override fun initListener() {
        Bt_zlogin.setOnClickListener{
            if(Et_userName.text.toString().equals("")||Et_passWord.text.toString().equals("")){
                ToastUtils.showLong("账号密码不能为空")
            }else if(SPUtil.getString("city").toString().equals("")) {
                ToastUtils.showLong("请打开定位权限")
            }else{
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().loginByAccount(Et_userName.text.toString(), Et_passWord.text.toString(),SPUtil.getString("city").toString())).subscribe({
                    SPUtil.saveString("token",it.token)
                    SPUtil.saveString("userid",Et_userName.text.toString())
                    SPUtil.saveString("rongyun_token",it.rongyun_token)
                    MyApplication.getInstance().setGetAlisa()
                    startActivity(MainActivity::class.java)
                    activity?.finish()
                },{
                    var message:Message = Message()
                    message.obj = it.message
                    handler.sendMessage(message);
                })
            }
        }
        Tv_signAccount.setOnClickListener {
            startActivity(RegisterActivity::class.java)
        }

        change_passwords.setOnClickListener {
            startActivity(ChangPassActivity::class.java)
        }
    }

    private fun connect(token: String) {
        RongIM.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onTokenIncorrect() {
                Log.e("LoginActivity", "--onTokenIncorrect")
            }

            override fun onSuccess(userid: String) {
                Log.e("LoginActivity", "--onSuccess--" + userid)
                //服务器连接成功，跳转消息列表

            }

            override fun onError(errorCode: RongIMClient.ErrorCode) {
                Log.e("LoginActivity", "--onError")
            }
        })
    }
    override fun initData() {
    }

    override fun initView() {
    }
}// Required empty public constructor