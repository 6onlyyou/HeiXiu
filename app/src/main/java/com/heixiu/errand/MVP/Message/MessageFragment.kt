package com.heixiu.errand.MVP.Message


import android.content.Intent
import android.os.Handler
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
import com.heixiu.errand.bean.QueryPersonalBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import io.rong.imkit.RongIM
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.fragment_message.*
import java.util.*
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.os.Bundle


class MessageFragment : BaseFragment() {
    var MainFragment: Fragment? = null
    var queryPersonalBean: QueryPersonalBean? = null
    private val mFragmentStack: Stack<Fragment> = Stack()
    override fun initView() {
    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_message, container, false)
        initRongMessage()
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
            val intent = Intent(context, RankListActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("datas", queryPersonalBean)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        message_wallet.setOnClickListener {
            startActivity(WalletMainActivity::class.java)
        }
        message_mymessager.setOnClickListener {
            startActivity(ConversationListActivity::class.java)
        }
    }


    override fun initData() {

    }
    /**
     * 融云消息接收，及初始化
     */
    private fun initRongMessage() {
        val conversationTypes = arrayOf(Conversation.ConversationType.PRIVATE, Conversation.ConversationType.DISCUSSION, Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM, Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE)

        val handler = Handler()
        handler.postDelayed({
            RongIM.getInstance().setOnReceiveUnreadCountChangedListener(mCountListener, *conversationTypes)
            //				RongIM.getInstance().setOnReceiveUnreadCountChangedListener(mCountListener1, Conversation.ConversationType.APP_PUBLIC_SERVICE);
        }, 500)


    }

    var mCountListener: RongIM.OnReceiveUnreadCountChangedListener = RongIM.OnReceiveUnreadCountChangedListener { count ->
        if (count == 0) {
            message_count.setVisibility(View.GONE)
        } else if (count > 0 && count < 100) {
            message_count.setVisibility(View.VISIBLE)
            message_count.setText(count.toString() + "")
        }
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
                queryPersonalBean = it
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
