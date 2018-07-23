package com.heixiu.errand.dialog

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import com.heixiu.errand.Event.PublishParamsChangeEvent
import com.heixiu.errand.MVP.Contentt.ContentFragment
import com.heixiu.errand.R
import com.heixiu.errand.databinding.ChooseWeightDialogBinding
import com.heixiu.errand.utils.RxBus

/**
 * Created by YuanGang on 2018/5/8.
 */
class AddPriceDialog(context: Context) : BottomDialog(context) {

    lateinit var binding: ChooseWeightDialogBinding
    val numbers = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.choose_weight_dialog, null, false)
        setContentView(binding.root)

        binding.moreWeightLayout.visibility = View.GONE

        binding.cancel.setOnClickListener({
            dismiss()
        })

        binding.confirm.setOnClickListener({
            if (binding.weightNp.value == 1) {
                ContentFragment.addPrice = 0.toString()
            } else {
                ContentFragment.addPrice = numbers[binding.weightNp.value - 1]
            }
            RxBus.getDefault().post(PublishParamsChangeEvent())
            dismiss()
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