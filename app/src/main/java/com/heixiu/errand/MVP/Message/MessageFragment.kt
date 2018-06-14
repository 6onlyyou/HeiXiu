package com.heixiu.errand.MVP.Message


import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.MVP.Message.wallet.WalletMainActivity
import com.heixiu.errand.MVP.Seting.SetingMainActivity
import com.heixiu.errand.MVP.common.TicketActivity

import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
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
        message_mytasks.setOnClickListener{
            startActivity(MyTaskActicity::class.java)
        }
        message_address.setOnClickListener{
            startActivity(AddressListActivity::class.java)
        }
        message_coupons.setOnClickListener {
            startActivity(TicketActivity::class.java)
        }
        message_ranking.setOnClickListener {
            startActivity(RankListActivity::class.java)
        }
        message_wallet.setOnClickListener {
            startActivity(WalletMainActivity::class.java)
        }

    }


    override fun initData() {

    }

    override fun onResume() {
        super.onResume()

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryPersonal(SPUtil.getString("userid"),SPUtil.getString("city"))).subscribe({
            message_tadayRank.text=it.platRank.toString()
                message_todayMenoy.text=it.dayAmount.toString()

            message_friendRank.text = it.friendRank.toString()
            Glide.with(this)
                .load(it.userImg)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(message_hard);
        },{
            ToastUtils.showLong(it.message)
        })
    }
}// Required empty public constructor
