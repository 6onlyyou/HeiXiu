package com.heixiu.errand.MVP.Contentt


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.common.TicketActivity
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.base.Contants
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.dialog.AddPriceDialog
import com.heixiu.errand.dialog.ChooseWeightDialog
import com.heixiu.errand.dialog.InputAddressDialog
import com.heixiu.errand.dialog.KeepPriceDialog
import kotlinx.android.synthetic.main.fragment_content.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : BaseFragment(), InputAddressDialog.OnAddressConfirm {

    override fun addressConfirm(address: String, type: String) {
        if (type.equals(Contants.RECEIVER)) {
            ContentFragment.receiveAddress = address
            receiveAddress.text = address
        }

        if (type.equals(Contants.SEND)) {
            sendAddress.text = address
            ContentFragment.sendAddress = address
        }
    }

    private val days = ArrayList<String>()
    private val hour = ArrayList<Int>()
    private val mintnues = ArrayList<Int>()


    companion object {
        var receiveAddress: String = ""
        var sendAddress: String = ""
        var recieveTime: String = ""
        var packageType: String = ""
        var packageWeight: String = ""
        var ticketId: String = "1"
        var discountCoupon: String = "5"
        var addPrice: String = ""
        var keepPrice: String = ""
        var receiverName = ""
        var receiverNum = ""
        var descriptions = ""
    }

    override fun initView() {

    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        days.add("今天")
        days.add("明天")

        (0..24 step 1).mapTo(hour) { it }
        (1..60 step 1).mapTo(mintnues) { it }

        return inflater!!.inflate(R.layout.fragment_content, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //取货地址
        receiveAddress.setOnClickListener({
            InputAddressDialog(context!!, this, Contants.RECEIVER).show()
        })
        //送货地址
        sendAddress.setOnClickListener({
            InputAddressDialog(context!!, this, Contants.SEND).show()
        })
        //送货时间
        sendTime.setOnClickListener({
            val pvNoLinkOptions: OptionsPickerView<Any> = OptionsPickerBuilder(context, object : OnOptionsSelectListener {
                override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                    dealTime(options1, options2, options3)
                }
            }).build()
            pvNoLinkOptions.setNPicker(days as List<Any>?, hour as List<Any>?, mintnues as List<Any>?)
            pvNoLinkOptions.setSelectOptions(0, 1, 1)
            pvNoLinkOptions.show()
        })
        //物品类型
        package_type_tv.setOnClickListener({
            PackageTypeActivity.startSelf(context!!)
        })
        //物品重量
        package_weight_tv.setOnClickListener({
            ChooseWeightDialog(context!!).show()
        })
        //物品优惠券
        discount_coupon_tv.setOnClickListener({
            TicketActivity.startSelf(context!!)
        })
        //加价
        add_price_tv.setOnClickListener({
            AddPriceDialog(context!!).show()
        })
        //保价
        keep_price_tv.setOnClickListener({
            KeepPriceDialog(context!!).show()
        })

        submit.setOnClickListener({
            if (TextUtils.isEmpty(ContentFragment.receiveAddress) ||
                    TextUtils.isEmpty(ContentFragment.sendAddress) ||
                    TextUtils.isEmpty(ContentFragment.recieveTime) ||
                    TextUtils.isEmpty(ContentFragment.packageType) ||
                    TextUtils.isEmpty(ContentFragment.packageWeight) ||
                    TextUtils.isEmpty(ContentFragment.ticketId)
                    || TextUtils.isEmpty(ContentFragment.discountCoupon)
                    || TextUtils.isEmpty(ContentFragment.addPrice)
                    || TextUtils.isEmpty(ContentFragment.keepPrice)
                    || TextUtils.isEmpty(ContentFragment.receiverName)
                    || TextUtils.isEmpty(ContentFragment.receiverNum)
                    || TextUtils.isEmpty(ContentFragment.descriptions)) {
                ToastUtils.showLong("信息不完整")
            } else {
                try {
                    var orderInfo = OrderInfo()
                    orderInfo.receiveAddress = ContentFragment.receiveAddress
                    orderInfo.sendAddress = ContentFragment.sendAddress
                    orderInfo.sendTime = ContentFragment.recieveTime
                    orderInfo.name = ContentFragment.packageType
                    orderInfo.addPrice = ContentFragment.addPrice.toInt()
                    orderInfo.supportPrice = ContentFragment.keepPrice.toInt()
                    orderInfo.receiveName = ContentFragment.receiverName
                    orderInfo.receiveNum = ContentFragment.receiverNum
                    orderInfo.weight = ContentFragment.packageWeight.toInt()
                    orderInfo.description = ContentFragment.descriptions

                    ConfirmPublishOrderActivity.startSelf(context!!, orderInfo)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun dealTime(options1: Int, options2: Int, options3: Int) {
        val c = Calendar.getInstance()//
        var mYear = c.get(Calendar.YEAR) // 获取当前年份
        var mMonth = c.get(Calendar.MONTH)// 获取当前月份
        var mDay = c.get(Calendar.DAY_OF_MONTH)// 获取当日期

        if (options1 == 0) {
            c.set(mYear, mMonth, mDay, hour[options2], mintnues[options3])
        }
        if (options1 == 1) {
            c.add(Calendar.DAY_OF_YEAR, 1)
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), hour[options2], mintnues[options3])
        }
        ContentFragment.recieveTime = c.timeInMillis.toString()
        sendTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(c.timeInMillis)
    }

    override fun initListener() {

    }

    var mSuggestionSearch: SuggestionSearch? = null

    override fun initData() {
//        SDKInitializer.initialize((MyApplication.getInstance()))
//        mSuggestionSearch = SuggestionSearch.newInstance()
//        mSuggestionSearch?.setOnGetSuggestionResultListener {
//            if (it == null || it.getAllSuggestions() == null) {
//                Log.i("result: ", "未找到相关结果\n")
//            } else {
//                var resl: List<SuggestionResult.SuggestionInfo> = it.getAllSuggestions();
//                for (suggestionInfo in resl) {
//                    Log.i("result: ", "city" + suggestionInfo.city + " dis " + suggestionInfo.district + "key " + suggestionInfo.key);
//                }
//
//            }
//            //获取在线建议检索结果
//        }
//        mSuggestionSearch?.requestSuggestion(SuggestionSearchOption()
//                .keyword("百度"))
    }


}
