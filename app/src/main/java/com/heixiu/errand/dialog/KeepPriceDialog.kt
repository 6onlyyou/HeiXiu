package com.heixiu.errand.dialog

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import com.heixiu.errand.MVP.Contentt.ContentFragment
import com.heixiu.errand.R
import com.heixiu.errand.databinding.KeepPriceDialogBinding

/**
 * Created by YuanGang on 2018/5/8.
 */
class KeepPriceDialog(context: Context) : BottomDialog(context) {

    lateinit var binding: KeepPriceDialogBinding
    val numbers = arrayOf("<5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"
            , "27", "28", "29", "30"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.keep_price_dialog, null, false)
        setContentView(binding.root)
        binding.dialog = this

        binding.cancel.setOnClickListener({
            dismiss()
        })

        binding.confirm.setOnClickListener({
            ContentFragment.keepPrice = binding.needPrice.text.toString()
            dismiss()
        })
    }
}