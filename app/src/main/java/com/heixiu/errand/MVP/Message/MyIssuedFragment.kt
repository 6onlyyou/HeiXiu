package com.heixiu.errand.MVP.Message


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heixiu.errand.MVP.Community.entity.DynamicEntity

import com.heixiu.errand.R
import com.heixiu.errand.adapter.CommounityAdapter
import com.heixiu.errand.adapter.MyIssuedAdapter
import com.heixiu.errand.base.BaseFragment
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_my_issued.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class MyIssuedFragment : BaseFragment() {
    internal var myIssuedAdapter: MyIssuedAdapter? = null
    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_my_issued, container, false)
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun initView() {
        initTitle("视频", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {

        })
//        Rv_issue.setLayoutManager(LinearLayoutManager(activity))
//        //如果Item高度固定  增加该属性能够提高效率
//        Rv_issue.setHasFixedSize(true)
//        //设置适配器
//        myIssuedAdapter = MyIssuedAdapter(R.layout.item_issued, dynamicEntityList)
//        //设置加载动画
//        myIssuedAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
//        //设置是否自动加载以及加载个数
//        myIssuedAdapter!!.openLoadMore(2, true)
//        //将适配器添加到RecyclerView
//        rv_list.setAdapter(myIssuedAdapter)
//        //设置自动加载监听
//        myIssuedAdapter!!.setOnLoadMoreListener(this)
//        val list = ArrayList<DynamicEntity>()
//        myIssuedAdapter = MyIssuedAdapter(list)
    }



}// Required empty public constructor
