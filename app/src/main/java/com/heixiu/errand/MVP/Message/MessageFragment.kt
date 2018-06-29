package com.heixiu.errand.MVP.Message


import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.ToastUtils
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


class MessageFragment : BaseFragment() {
    var MainFragment: Fragment? = null
    private val mFragmentStack: Stack<Fragment> = Stack()
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
        message_coupons.setOnClickListener {
            startActivity(TicketActivity::class.java)
        }
        message_issued.setOnClickListener {
            startActivity(MyIssuedFragment::class.java)
        }
        message_mytasks.setOnClickListener {
            startActivity(MyTaskActicity::class.java)
        }
        message_address.setOnClickListener {
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

        if (SPUtil.getString("userid").equals("") || SPUtil.getString("userid").equals("1")) {
            message_tadayRank.text = ""
            message_todayMenoy.text = ""
            message_friendRank.text = ""
            Glide.with(this)
                    .load(R.mipmap.defaulthead)
                    .crossFade()
                    .placeholder(R.mipmap.defaulthead)
                    .into(message_hard);
        } else {
            Glide.with(this)
                    .load(SPUtil.getString("headurl"))
                    .crossFade()
                    .placeholder(R.mipmap.defaulthead)
                    .into(message_hard);
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryPersonal(SPUtil.getString("userid"), SPUtil.getString("city"))).subscribe({
                message_tadayRank.text = it.platRank.toString()
                message_todayMenoy.text = it.dayAmount.toString()
                message_friendRank.text = it.friendRank.toString()
                if(SPUtil.getString("headurl").equals("")) {
                    Glide.with(this)
                            .load(it.userImg)
                            .crossFade()
                            .placeholder(R.mipmap.defaulthead)
                            .into(message_hard);
                }
            }, {
                ToastUtils.showLong(it.message)
            })
        }
    }
}// Required empty public constructor
