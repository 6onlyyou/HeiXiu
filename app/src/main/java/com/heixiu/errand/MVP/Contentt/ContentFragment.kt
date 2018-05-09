package com.heixiu.errand.MVP.Contentt


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.heixiu.errand.MVP.common.TicketActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.dialog.AddPriceDialog
import com.heixiu.errand.dialog.ChooseWeightDialog
import com.heixiu.errand.dialog.KeepPriceDialog
import kotlinx.android.synthetic.main.fragment_content.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : BaseFragment() {

    private val days = ArrayList<String>()
    private val hour = ArrayList<String>()
    private val mintnues = ArrayList<String>()

    override fun initView() {
    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //取货地址
        receiveAddress.setOnClickListener({

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


            pvNoLinkOptions.setSelectOptions(0, 1, 1)
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
