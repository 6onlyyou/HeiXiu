package com.heixiu.errand.MVP.Login.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.RegisterActivity
import com.heixiu.errand.MainActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_account_login.*
import kotlinx.android.synthetic.main.fragment_phone_login.*

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

    override fun initListener() {
        Bt_zlogin.setOnClickListener{
            if(Et_userName.text.toString().equals("")||Et_passWord.text.toString().equals("")){
                ToastUtils.showLong("账号密码不能为空")
            }else{
                startActivity(MainActivity::class.java)
            }
        }
        Tv_signAccount.setOnClickListener {
            startActivity(RegisterActivity::class.java)
        }
    }

    override fun initData() {
    }

    override fun initView() {
    }
}// Required empty public constructor