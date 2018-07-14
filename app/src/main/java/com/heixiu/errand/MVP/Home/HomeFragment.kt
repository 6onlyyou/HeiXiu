package com.heixiu.errand.MVP.Home


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.Event.MyLocationEvent
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.MVP.Seting.WebActivity
import com.heixiu.errand.R
import com.heixiu.errand.adapter.HomeAdapter
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.GlideImageLoader
import com.heixiu.errand.utils.RxBus
import com.heixiu.errand.utils.SPUtil
import com.youth.banner.Transformer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    private val homeAdapter by lazy {
        HomeAdapter(context)
    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
        requestData()
    }

    private fun requestData() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryCreatedOrderInfo()).subscribe({
            homeAdapter.setData(it)
        }, {
            ToastUtils.showLong(it.message)
        })

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().banner).subscribe({
            banner.setOnBannerListener { position ->
                WebActivity.startSelf(context, "详情", it[position].link)
            }
            var bannerUrl = ArrayList<String>()
            it.mapTo(bannerUrl) { it.imgSrc }
            banner.setImages(bannerUrl)
            banner.start()
        }, {
            ToastUtils.showLong(it.message)
        })
    }


    var subscribe: Disposable? = null
    override fun initView() {
        subscribe = RxBus.getDefault().toObservable(MyLocationEvent::class.java).subscribe({
            homeAdapter.notifyDataSetChanged()
        }, {
            homeAdapter.notifyDataSetChanged()
        })

        homeRv.layoutManager = LinearLayoutManager(context)
        homeAdapter.onItemClick = object : HomeAdapter.OnItemClick {
            override fun onDetailClick(position: Int) {
                OrderDetailActivity.startSelf(context!!, homeAdapter.data[position])
            }

            override fun onConfirmOrderClick(position: Int) {
                if (TextUtils.isEmpty(SPUtil.getString("userid"))) {
                    startActivity(Intent(context, LoginActivity::class.java))
                } else {
                    takeOrder(homeAdapter.data[position])
                }
            }
        }
        homeRv.adapter = homeAdapter

        banner.isAutoPlay(true)
        banner.setImageLoader(GlideImageLoader())
        banner.setBannerAnimation(Transformer.ScaleInOut)
        banner.setDelayTime(5000)

    }

    fun takeOrder(orderInfo: OrderInfo) {

        if (SPUtil.getString("userid").equals(orderInfo.publishUserInfo.userId)) {
            ToastUtils.showShort("不能接自己发的订单呦~")
            return
        }
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().changeOrderStatus(orderInfo.orderNum, SPUtil.getString("userid"), "1"))
                .subscribe({
                    ConfirmOrderActivity.startSelf(context!!, orderInfo)
                }, {
                    ToastUtils.showShort(it.message)
                })
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        if (subscribe != null) {
            subscribe?.dispose()
        }
    }

}
