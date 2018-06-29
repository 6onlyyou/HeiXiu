package com.heixiu.errand.MVP.Message

import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.CommounityAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.MessageInfoBean
import com.heixiu.errand.bean.PubLishInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_other_personal_page.*

class OtherPersonalPageActivity : BaseActivity() {
    internal var commounityAdapter: CommounityAdapter? = null
    internal var messageInfoBean: MessageInfoBean?=null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_other_personal_page)
        initTitle("他人信息", R.color.colorAccent, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn) { finishWithAnim() }
    }

    override fun findViewById() {
        rv_list.setLayoutManager(LinearLayoutManager(this))
        //如果Item高度固定  增加该属性能够提高效率
        rv_list.setHasFixedSize(true)
        //设置适配器
        val list = ArrayList<PubLishInfo>()
        commounityAdapter = CommounityAdapter(list,"","")
        personal_compile.setOnClickListener {
            startActivity(EditProfileActivity::class.java)
        }
    }

    override fun setListener() {
        community_attention.setOnClickListener {
        if (SPUtil.getString("userid") == messageInfoBean!!.userInfo.userId){
            ToastUtils.showLong("不能关注自己")
            return@setOnClickListener
        }
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addFollow(SPUtil.getString("userid"), messageInfoBean!!.userInfo.userId)).subscribe({
            if (community_attention.text == "已关注") {
                ToastUtils.showLong("取消成功")
                community_attention.text = "关注"
            } else {
                ToastUtils.showLong("关注成功")
                community_attention.text = "已关注"
            }
        }) { throwable -> ToastUtils.showLong(throwable.message) }
//        page_fansonclick.setOnClickListener {
//            startActivity(MyFansActivity::class.java)
//        }
        }
    }

    override fun processLogic() {
    }

    override fun onResume() {
        super.onResume()
        val friendid = intent.getStringExtra("friendid")
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryMyMessage(friendid, null)).subscribe({
            messageInfoBean = it
            page_nickname.text = it.userInfo.nickName
            page_sign.text = it.userInfo.sign
            page_briday.text = it.userInfo.birthday
            page_fans.text = it.userFanCounts.toString()
            page_attention.text = it.userFollowsCount.toString()
            page_orderInfoPublishCount.text = "发单任务数："+it.orderInfoPublishCount.toString()
            page_orderInfoReceiveCount.text = "接单数："+it.orderInfoReceiveCount.toString()

            if (it.userInfo.sex.equals("男")){
                Glide.with(this)
                        .load(R.mipmap.ic_launcher)
                        .crossFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(page_sex);
            }else{
                Glide.with(this)
                        .load(R.mipmap.page_woman)
                        .crossFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(page_sex);
            }

            if(it.publishInfos.size>0){

                rv_list.setLayoutManager(LinearLayoutManager(this))
//        如果Item高度固定  增加该属性能够提高效率
                rv_list.setHasFixedSize(true)
//        设置适配器
                commounityAdapter = CommounityAdapter(it.publishInfos,it.userInfo.userImg,it.userInfo.nickName)
                //设置加载动画
                commounityAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                //设置是否自动加载以及加载个数
                //将适配器添加到RecyclerView
                rv_list.setAdapter(commounityAdapter)
                //设置自动加载监听
            }
        }) { throwable ->
            ToastUtils.showShort(throwable.message)
        }
    }
}