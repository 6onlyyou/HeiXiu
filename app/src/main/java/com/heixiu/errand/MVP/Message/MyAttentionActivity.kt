package com.heixiu.errand.MVP.Message

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.AttentionAdapter
import com.heixiu.errand.adapter.FansAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.MyAttentionBean
import com.heixiu.errand.bean.MyFansBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_my_fans.*

class MyAttentionActivity : BaseActivity() {
    internal var fansAdapter: AttentionAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_my_fans)
        initTitle("我的关注", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })
        val list = ArrayList<MyAttentionBean>()
        fansAdapter = AttentionAdapter(list)
        fans_list.setAdapter(fansAdapter)
    }

    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().followList(SPUtil.getString("userid"))).subscribe({
            if(it.size>0){
                fans_no.visibility= View.GONE
                fans_list.visibility= View.VISIBLE
                fans_list.setLayoutManager(LinearLayoutManager(this))
//        如果Item高度固定  增加该属性能够提高效率
                fans_list.setHasFixedSize(true)
//        设置适配器
                fansAdapter = AttentionAdapter(it)
                //设置加载动画
                fansAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                //设置是否自动加载以及加载个数
                //将适配器添加到RecyclerView
                fans_list.setAdapter(fansAdapter)
                fansAdapter!!.setNewData(it)
                //设置自动加载监听
            }
        },{
            ToastUtils.showLong(it.message)
        })
    }
}