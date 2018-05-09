package com.heixiu.errand.dialog

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import com.heixiu.errand.R
import com.heixiu.errand.databinding.ChooseWeightDialogBinding

/**
 * Created by YuanGang on 2018/5/8.
 */
class AddPriceDialog(context: Context) : BottomDialog(context) {

    lateinit var binding: ChooseWeightDialogBinding
    val numbers = arrayOf("5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.choose_weight_dialog, null, false)
        setContentView(binding.root)


        binding.moreWeightLayout.visibility = View.GONE

        binding.cancel.setOnClickListener({
            dismiss()
        })

        binding.confirm.setOnClickListener({
            binding.weightNp.verticalScrollbarPosition
        })

        //设置需要显示的内容数组
        binding.weightNp.displayedValues = numbers
        //设置最大最小值
        binding.weightNp.minValue = 1
        binding.weightNp.maxValue = numbers.size
        //设置默认的位置
        binding.weightNp.value = 1
        binding.weightNp.wrapSelectorWheel = false
        binding.weightNp.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    }
}