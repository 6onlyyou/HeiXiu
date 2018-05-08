package com.heixiu.errand.MVP.Contentt


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.dialog.ChooseWeightDialog
import kotlinx.android.synthetic.main.fragment_content.*


/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : BaseFragment() {
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

        })
        //物品类型
        package_type_tv.setOnClickListener({
            PackageTypeActivity.startSelf(context)
        })
        //物品重量
        package_weight_tv.setOnClickListener({
            ChooseWeightDialog(context!!).show()
        })
        //物品优惠券
        discount_coupon_tv.setOnClickListener({

        })
        //加价
        add_price_tv.setOnClickListener({

        })
        //保价
        keep_price_tv.setOnClickListener({

        })

    }

    override fun initListener() {

    }

    override fun initData() {
    }




}
