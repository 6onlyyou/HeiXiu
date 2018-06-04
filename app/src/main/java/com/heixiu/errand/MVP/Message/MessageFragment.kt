package com.heixiu.errand.MVP.Message


import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heixiu.errand.MVP.Seting.SetingMainActivity
import com.heixiu.errand.MVP.common.TicketActivity

import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_message.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : BaseFragment() {
    var MainFragment: Fragment? = null
    private val mFragmentStack: Stack<Fragment> = Stack()//自己维护要处理Fragment的栈,这么做的好处是可以灵活的使用里面保存的Fragmtnt
    override fun initView() {
    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_message, container, false)
    }

    override fun initListener() {
        message_hard.setOnClickListener {
            startActivity(PersonalPageActivity::class.java)
        }
        message_setting.setOnClickListener {
            startActivity(SetingMainActivity::class.java)
        }
        message_coupons.setOnClickListener{
            startActivity(TicketActivity::class.java)
        }
        message_issued.setOnClickListener{
            startActivity(MyIssuedFragment::class.java)
        }

    }


    override fun initData() {

    }


}// Required empty public constructor
