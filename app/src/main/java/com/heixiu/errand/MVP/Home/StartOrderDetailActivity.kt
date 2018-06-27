package com.heixiu.errand.MVP.Home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.dialog.CommonCenterDialog
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_start_order_detail.*


class StartOrderDetailActivity : AppCompatActivity() {


    private var orderInfo: OrderInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_order_detail)

        orderInfo = intent.getSerializableExtra("data") as OrderInfo

        back.setOnClickListener({ finish() })

        orderNo.text = "订单编号：" + orderInfo!!.orderNum
        start.text = orderInfo!!.sendAddress
        end.text = orderInfo!!.receiveAddress
        time.text = orderInfo!!.sendTime
        type.text = orderInfo?.name
        weight.text = orderInfo?.weight.toString()
        add_money.text = "加价" + orderInfo!!.addPrice
        tips.text = orderInfo!!.description
        recipientsame.text = "收件人姓名：" + orderInfo!!.receiveName
        recipientsNum.text = "收件人电话：" + orderInfo!!.receiveNum
        courierNum.text = "快递员电话：" + orderInfo!!.courierNum
        receiveName.text = orderInfo?.receiveName

        message.setOnClickListener({
            val uri = Uri.parse("smsto:" + orderInfo?.receiveNum)
            val it = Intent(Intent.ACTION_SENDTO, uri)
            it.putExtra("sms_body", "")
            startActivity(it)
        })

        call.setOnClickListener({
            //            val intent = Intent()
//            intent.action = Intent.ACTION_CALL
//            intent.data = Uri.parse("tel:" + orderInfo?.receiveNum)
//            startActivity(intent)
            requestPression()
        })

        take_order.setOnClickListener({
            dealTakeOrderClick()
        })

        startMap.setOnClickListener({
            OrderMapActivity.startSelf(this@StartOrderDetailActivity, orderInfo)
        })
    }


    fun requestPression() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                // 返回值：
                //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show()
                // 帮跳转到该应用的设置界面，让用户手动授权
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri1 = Uri.fromParts("package", packageName, null)
                intent.setData(uri1)
                startActivity(intent)
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CALL_PHONE), 1)
            }
        } else {
            // 已经获得授权，可以打电话
            val uri = Uri.parse("tel:" + orderInfo?.userId)
            val intent2 = Intent(Intent.ACTION_CALL, uri)
            startActivity(intent2)
        }
    }

    companion object {
        fun startSelf(context: Context, orderInfo: OrderInfo) {
            val intent = Intent(context, StartOrderDetailActivity::class.java)
            intent.putExtra("data", orderInfo)
            context.startActivity(intent)
        }
    }

    fun takeOrder(orderInfo: OrderInfo, orderStatus: String) {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().changeOrderStatus(orderInfo.orderNum, SPUtil.getString("userid"), orderStatus))
                .subscribe({
                    if (orderStatus.equals("2")) {
                        CommonCenterDialog(this@StartOrderDetailActivity, "2")
                    }
                    if (orderStatus.equals("3")) {
                        CommonCenterDialog(this@StartOrderDetailActivity, "3")
                    }
                    queryOneOrderInfo()
                }, {
                    ToastUtils.showShort(it.message)
                })
    }

    override fun onResume() {
        super.onResume()
        queryOneOrderInfo()
    }

    fun queryOneOrderInfo() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryOneOrderInfo(orderInfo?.orderNum))
                .subscribe({
                    this.orderInfo = it
                    dealOrderState()
                    if ("0" == orderInfo?.orderStatus) {

                    }
                }, {

                })
    }

    private fun dealOrderState() {
        when (orderInfo?.orderStatus) {
            "0" -> take_order.setImageResource(R.mipmap.ic_start_order)
            "1" -> take_order.setImageResource(R.mipmap.btn_get_doll)
            "2" -> take_order.setImageResource(R.mipmap.ic_confirm_arrive)
            "3" -> take_order.setImageResource(R.mipmap.ic_confirm_arrive)
            "4" -> take_order.setImageResource(R.mipmap.ic_confirm_arrive)
        }
    }

    private fun dealTakeOrderClick() {
        when (orderInfo?.orderStatus) {
            "1" -> takeOrder(orderInfo!!, "2")
            "2" -> takeOrder(orderInfo!!, "3")
        }
    }
}
