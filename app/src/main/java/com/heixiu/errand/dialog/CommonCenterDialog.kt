package com.heixiu.errand.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.heixiu.errand.R
import com.heixiu.errand.databinding.CommonCenterDialogBinding

/**
 * Created by YuanGang on 2018/4/18.
 */
open class CommonCenterDialog(context: Context, orderStatus: String) : Dialog(context, R.style.CustomDialogStyle) {
    init {
        val dialogWindow = window
        dialogWindow.setGravity(Gravity.CENTER)
        dialogWindow.decorView.setPadding(0, 0, 0, 0)
        val lp = dialogWindow.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialogWindow.attributes = lp
    }

    var orderState = orderStatus
    lateinit var binding: CommonCenterDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.common_center_dialog, null, false)
        setContentView(binding.root)

        if (orderState.equals("2")) {
            binding.title.text = "您的速度超快呢，快去赶往指定送达地点吧~"
        }
        if (orderState.equals("1")) {
            binding.title.text = "订单已开始~"
        }
        if (orderState.equals("3")) {
            binding.title.text = "任务完成~"
        }
        binding.cancel.setOnClickListener({
            dismiss()
        })

        binding.confirm.setOnClickListener({
            dismiss()
        })
    }
}