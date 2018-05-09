package com.heixiu.errand.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.heixiu.errand.R
import kotlinx.android.synthetic.main.input_address_dialog.view.*
import com.heixiu.errand.databinding.InputAddressDialogBinding

/**
 * Created by YuanGang on 2018/5/8.
 */
class InputAddressDialog(context: Context) : Dialog(context, R.style.CustomDialogStyle) {

    lateinit var binding: InputAddressDialogBinding

    init {
        val dialogWindow = window
        dialogWindow.setGravity(Gravity.TOP)
        dialogWindow.decorView.setPadding(0, 60, 0, 0)
        val lp = dialogWindow.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialogWindow.attributes = lp
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.input_address_dialog, null, false)
        setContentView(binding.root)

//        binding.cancel.setOnclick

    }
}