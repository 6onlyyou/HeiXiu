package com.heixiu.errand.MVP.Message

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.RankListAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.QueryPersonalBean
import com.heixiu.errand.bean.RankBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_rank_list.*
import java.io.Serializable

class RankListActivity : BaseActivity() {
    var queryPersonalBean: QueryPersonalBean? = null
    internal var rankListAdapter: RankListAdapter? = null
    companion object {
        fun startSelf(context: Context, orderInfo: QueryPersonalBean) {
            val intent = Intent(context, RankListActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }
    override fun loadViewLayout() {
        setContentView(R.layout.activity_rank_list)
        initTitle("排行榜", R.color.colorPrimary, R.color.white)
        if(intent.getSerializableExtra("datas") != null){
            queryPersonalBean = intent.getSerializableExtra("datas") as QueryPersonalBean
        }

        rand_tadayRank.text = queryPersonalBean!!.platRank.toString()
        rand_todayMenoy.text = queryPersonalBean!!.dayAmount.toString()
        rand_friendRank.text = queryPersonalBean!!.friendRank.toString()
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        dollStateTb.addTab(dollStateTb.newTab().setText("平台排行").setTag("1"))
        dollStateTb.addTab(dollStateTb.newTab().setText("好友排行").setTag("2"))
        val list = ArrayList<RankBean>()
        rankListAdapter = RankListAdapter(list)
        my_doll_rv.setAdapter(rankListAdapter)
        dollStateTb.isSelected = true
        dollStateTb.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.tag == "1") {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().platRank(SPUtil.getString("userid"),SPUtil.getString("city"))).subscribe({
                        if(it.size>0){
                            //设置自动加载监听
                            my_doll_rv.setLayoutManager(LinearLayoutManager(this@RankListActivity))
//        如果Item高度固定  增加该属性能够提高效率
                            my_doll_rv.setHasFixedSize(true)
//        设置适配器
                            rankListAdapter = RankListAdapter(it)
                            //设置加载动画
                            rankListAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                            //设置是否自动加载以及加载个数
                            //将适配器添加到RecyclerView
                            my_doll_rv.setAdapter(rankListAdapter)
                            rankListAdapter!!.setNewData(it)
                        }
                    },{
                        ToastUtils.showLong(it.message)
                    })

                }
                if (tab.tag == "2") {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().friendRank(SPUtil.getString("userid"))).subscribe({
                        if(it.size>0){
                            //设置自动加载监听
                            my_doll_rv.setLayoutManager(LinearLayoutManager(this@RankListActivity))
//        如果Item高度固定  增加该属性能够提高效率
                            my_doll_rv.setHasFixedSize(true)
//        设置适配器
                            rankListAdapter = RankListAdapter(it)
                            //设置加载动画
                            rankListAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                            //设置是否自动加载以及加载个数
                            //将适配器添加到RecyclerView
                            my_doll_rv.setAdapter(rankListAdapter)
                            rankListAdapter!!.setNewData(it)
                        }
                    },{
                        ToastUtils.showLong(it.message)
                    })
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
