package com.heixiu.errand.MVP.Message.myorder

import android.content.Context
import android.content.Intent
import com.baidu.mapapi.map.BaiduMap
import com.heixiu.errand.MVP.Home.OrderMapActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.OrderInfo
import kotlinx.android.synthetic.main.activity_order_sending.*

class OrderSendingActivity : BaseActivity() {

    fun startSelf(context: Context, orderInfo: OrderInfo) {
        val intent = Intent(context, OrderMapActivity::class.java)
        intent.putExtra("data", orderInfo)
        context.startActivity(intent)
    }

    override fun processLogic() {

    }

    override fun setListener() {
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_order_sending)
    }

    lateinit var mBaiduMap: BaiduMap
    lateinit var orderInfo: OrderInfo
    override fun findViewById() {
        mBaiduMap = map.map

        orderInfo = intent.getSerializableExtra("data") as OrderInfo
        orderNo.text = orderInfo.orderNum
        name.setText(orderInfo.receiveName)
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        map.onPause()
    }

}
