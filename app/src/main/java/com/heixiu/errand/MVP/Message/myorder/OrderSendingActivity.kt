package com.heixiu.errand.MVP.Message.myorder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.EventLogTags
import com.baidu.mapapi.map.BaiduMap
import com.heixiu.errand.MVP.Home.OrderMapActivity
import com.heixiu.errand.MVP.Seting.DescriptionActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.utils.SPUtil
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_order_sending.*

class OrderSendingActivity : BaseActivity() {
    companion object {
        fun startSelf(context: Context, orderInfo: OrderInfo) {
            val intent = Intent(context, OrderSendingActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
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
        order_feedback.setOnClickListener {
            startActivity(DescriptionActivity::class.java,orderInfo.orderNum)
        }
        message.setOnClickListener {
            RongIM.getInstance().setMessageAttachedUserInfo(true)
            RongIM.getInstance().setCurrentUserInfo(UserInfo(SPUtil.getString("userid"), SPUtil.getString("nickname"), Uri.parse(SPUtil.getString("headurl").toString())))
            RongIM.getInstance().startPrivateChat(this, "18757161476",orderInfo!!.receiveName)
        }
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
