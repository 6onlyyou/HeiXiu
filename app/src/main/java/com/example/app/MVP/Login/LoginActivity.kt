package com.example.app.MVP.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.example.app.R
import com.example.app.base.BaseActivity
import com.example.app.MVP.Contentt.ContentFragment
import com.example.app.MVP.Login.fragment.AccountLoginFragment
import com.example.app.MVP.Login.fragment.PhoneLoginFragment
import com.example.app.MainActivity
import com.example.app.adapter.LoginFragmentAdapter
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    var  adapter:LoginFragmentAdapter? = null;
    var  fragments:ArrayList<Fragment>? = ArrayList();
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
    fun loginTo(view: View) {
        startActivity(MainActivity::class.java)
//        homePresenter!!.logOut()
    }
}
