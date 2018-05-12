package com.heixiu.errand.MVP.Contentt


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.heixiu.errand.MVP.common.TicketActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.dialog.AddPriceDialog
import com.heixiu.errand.dialog.ChooseWeightDialog
import com.heixiu.errand.dialog.InputAddressDialog
import com.heixiu.errand.dialog.KeepPriceDialog
import kotlinx.android.synthetic.main.fragment_content.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : BaseFragment() {

    private val days = ArrayList<String>()
    private val hour = ArrayList<String>()
    private val mintnues = ArrayList<String>()


    companion object {
        var receiveAddress: String = ""
        var sendAddress: String = ""
        var recieveTime: String = ""
        var packageType: String = ""
        var packageWeight: String = ""
        var discountCoupon: String = ""
        var addPrice: String = ""
        var keepPrice: String = ""
    }

    override fun initView() {
    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        days.add("今天")
        days.add("明天")

        (0..24 step 1).mapTo(hour) { it.toString() }
        (1..60 step 1).mapTo(mintnues) { it.toString() }


        return inflater!!.inflate(R.layout.fragment_content, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //取货地址
        receiveAddress.setOnClickListener({
            InputAddressDialog(context!!).show()
        })
        //送货地址
        sendAddress.setOnClickListener({

        })
        //送货时间
        sendTime.setOnClickListener({
            val pvNoLinkOptions: OptionsPickerView<Any> = OptionsPickerBuilder(context, object : OnOptionsSelectListener {
                override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {

                }
            }).build()
            pvNoLinkOptions.setNPicker(days as List<Any>?, hour as List<Any>?, mintnues as List<Any>?);
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

    }

    override fun initListener() {

    }

    override fun initData() {
    }


}
