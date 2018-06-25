package com.heixiu.errand.MVP.Message


import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heixiu.errand.MVP.Community.entity.DynamicEntity
import com.heixiu.errand.MainActivity

import com.heixiu.errand.R
import com.heixiu.errand.adapter.CommounityAdapter
import com.heixiu.errand.adapter.MyIssuedAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_my_issued.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class MyIssuedFragment : BaseActivity()  {
    internal var myIssuedAdapter: MyIssuedAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.fragment_my_issued)
    }

    override fun findViewById() {
        initTitle("我的发布", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
        finishWithAlpha()
        })
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().MyIssued(SPUtil.getString("userid"))).subscribe({
            Rv_issue.setLayoutManager(LinearLayoutManager(this))
//        如果Item高度固定  增加该属性能够提高效率
            Rv_issue.setHasFixedSize(true)
//        设置适配器
            myIssuedAdapter = MyIssuedAdapter(it)
            //设置加载动画
            myIssuedAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
            //设置是否自动加载以及加载个数
            //将适配器添加到RecyclerView
            Rv_issue.setAdapter(myIssuedAdapter)
            //设置自动加载监听
            myIssuedAdapter!!.setNewData(it)
        },{

        })
    }

    override fun setListener() {

    }

    override fun processLogic() {
    }







//        mMyDollRv.setLayoutManager(GridLayoutManager(mContext, 2))
//        val list = ArrayList<MyDollEntity>()
//        mAdapter = MyDollAdapter(list)
//        mAdapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener() {
//            fun onItemClick(adapter: BaseQuickAdapter, view: View, position: Int) {
//
//                if (adapter.getItem(position) != null && (adapter.getItem(position) as MyDollEntity).isIn_invalid()) {
//                    return
//                }
//
//                if (mAdapter.isSelect) {
//                    val entity = adapter.getItem(position) as MyDollEntity
//                    if (!entity.isInOrder()) {
//                        entity.setChecked(!entity.isChecked())
//                        adapter.notifyItemChanged(position)
//                    }
//                } else {
//                    val entity = adapter.getItem(position) as MyDollEntity
//                    val url = Urls.URL_SKU_DETAIL + entity.getGoods().getId()
//                    WebActivity.runActivity(mContext, "娃娃详情", url)
//                }
//            }
//        })



}// Required empty public constructor
