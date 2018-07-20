package com.heixiu.errand.MVP.Message


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.heixiu.errand.R
import com.heixiu.errand.adapter.MyIssuedAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_my_issued.*


/**
 * A simple [Fragment] subclass.
 */
class MyIssuedFragment : BaseActivity() {
    internal var myIssuedAdapter: MyIssuedAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.fragment_my_issued)
    }

    override fun findViewById() {
        initTitle("我的发布", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, { finishWithAlpha() })
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().MyIssued(SPUtil.getString("userid"))).subscribe({
            Rv_issue.layoutManager = LinearLayoutManager(this)
            Rv_issue.setHasFixedSize(true)
            myIssuedAdapter = MyIssuedAdapter(it)
            myIssuedAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
            Rv_issue.adapter = myIssuedAdapter
            myIssuedAdapter!!.setNewData(it)
        }, {

        })
    }

    override fun setListener() {

    }

    override fun processLogic() {
    }
}
