package com.heixiu.errand.MVP.Login

import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.MVP.Login.fragment.AccountLoginFragment
import com.heixiu.errand.MVP.Login.fragment.PhoneLoginFragment
import com.heixiu.errand.MainActivity
import com.heixiu.errand.adapter.LoginFragmentAdapter
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    var  adapter:LoginFragmentAdapter? = null;
    var  fragments:ArrayList<android.support.v4.app.Fragment>? = ArrayList();
    override fun loadViewLayout() {
        setContentView(R.layout.activity_login)
        initTitle("登入", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        val fragment1 = PhoneLoginFragment()
        val fragment2 = AccountLoginFragment()
        fragments!!.add(fragment1)
        fragments!!.add(fragment2)
        adapter = LoginFragmentAdapter(supportFragmentManager, fragments)
        viewpager.setAdapter(adapter)
        tablayout.setupWithViewPager(viewpager)
    }

    override fun findViewById() {

    }

    override fun setListener() {

    }

    override fun processLogic() {

    }

}
