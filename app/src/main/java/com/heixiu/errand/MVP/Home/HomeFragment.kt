package com.heixiu.errand.MVP.Home


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.HomeAdapter
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
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
    }

    override fun initView() {
        homeRv.layoutManager = LinearLayoutManager(context)
        homeAdapter.onItemClick = object : HomeAdapter.OnItemClick {
            override fun onDetailClick(position: Int) {
//                OrderDetailActivity.startSelf(context!!, homeAdapter.data[position])
                OrderDetailActivity.startSelf(context!!, OrderInfo())
            }

            override fun onConfirmOrderClick(position: Int) {
//                ConfirmOrderActivity.startSelf(context!!, homeAdapter.data[position])
                var orderInfo = OrderInfo()
                orderInfo.addPrice = 1
                orderInfo.courierNum = "1"
                orderInfo.description = "2"
                orderInfo.name = "111"
                orderInfo.weight = 11
                orderInfo.payment = 222
                orderInfo.receiveAddress = "bbbbb"
                orderInfo.sendAddress = "aaaaa"
                orderInfo.supportPrice = 11
                orderInfo.orderNum = "12312321"
                orderInfo.courierNum = 1385555.toString()
                orderInfo.receiveNum = 1367744444.toString()
                orderInfo.receiveName = "黎明"
//                orderInfo.destinationsLatitude =
                ConfirmOrderActivity.startSelf(context!!, orderInfo)
            }
        }
        homeRv.adapter = homeAdapter
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}
