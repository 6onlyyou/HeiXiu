package com.heixiu.errand.MVP.Home


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.Event.MyLocationEvent
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.MVP.Seting.WebActivity
import com.heixiu.errand.MyApplication.MyApplication
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
        if(number>0){
            requestData()
        }

    }

    private fun requestData() {
        Log.i("homeFFFF","requestData")

        refresh.isRefreshing = true

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryCreatedOrderInfo()).subscribe({
            if(refresh==null){
                return@subscribe
            }
            refresh.isRefreshing = false

            val orderInfos = it.iterator()
            while (orderInfos.hasNext()) {
                val orderInfo = orderInfos.next()
                if (MyApplication.getInstance().localLat != 0.0) {
                    val distance = DistanceUtil.getDistance(
                            LatLng(MyApplication.getInstance().localLat, MyApplication.getInstance().localLong), LatLng(orderInfo.originsLatitude, orderInfo.originsLongitude))
                    if (distance > 5000 && orderInfo.userId != SPUtil.getString("userid")) {
                        orderInfos.remove()
                    }
//                    if (distance > 5000 && orderInfo.userId != SPUtil.getString("userid")) {
//                        orderInfos.remove()
//                    }
                }
            }

            if (it.size > 0) {
                homeAdapter.setData(it)
                emptyHomeView.visibility = View.GONE
                homeRv.visibility = View.VISIBLE
            } else {
                emptyHomeView.visibility = View.VISIBLE
                homeRv.visibility = View.GONE
            }
        }, {
            refresh.isRefreshing = false
            ToastUtils.showLong(it.message)
        })

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().banner).subscribe({
            if(banner==null){
                return@subscribe
            }
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
    var number = 0
    override fun initView() {
        subscribe = RxBus.getDefault().toObservable(MyLocationEvent::class.java).subscribe({

            if(number == 0){
                homeAdapter.setData(ArrayList())
                requestData()
                number++
            }

        }, {
            homeAdapter.notifyDataSetChanged()
        })

        homeRv.layoutManager = LinearLayoutManager(context)
        homeAdapter.onItemClick = object : HomeAdapter.OnItemClick {
            override fun onDetailClick(position: Int) {
                if(SPUtil.getString("disable").equals("1")){
                    startActivity(LoginActivity::class.java)
                    activity!!.finish();
                }else{
                    OrderDetailActivity.startSelf(context!!, homeAdapter.data[position])
                }

            }

            override fun onConfirmOrderClick(position: Int) {
                if(SPUtil.getString("disable").equals("1")){
                    startActivity(LoginActivity::class.java)
                    activity!!.finish();
                }else {
                    if (TextUtils.isEmpty(SPUtil.getString("userid"))) {
                        startActivity(Intent(context, LoginActivity::class.java))
                    } else {
                        takeOrder(homeAdapter.data[position])
                    }
                }
            }
        }
        homeRv.adapter = homeAdapter

        banner.isAutoPlay(true)
        banner.setImageLoader(GlideImageLoader())
        banner.setBannerAnimation(Transformer.ScaleInOut)
        banner.setDelayTime(5000)

        refresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                requestData()
            }
        })
    }

    fun takeOrder(orderInfo: OrderInfo) {

        if (MyApplication.getInstance().localLat == 0.0) {
            ToastUtils.showShort("暂时未获取到您的定位")
            return
        }
        val distance = DistanceUtil.getDistance(
                LatLng(MyApplication.getInstance().localLat, MyApplication.getInstance().localLong), LatLng(orderInfo.originsLatitude, orderInfo.originsLongitude))
        if (distance > 5000) {
            ToastUtils.showShort("该订单距离您超过八公里，不能接单")
            return
        }

        if (SPUtil.getString("userid").equals(orderInfo.userId)) {
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
