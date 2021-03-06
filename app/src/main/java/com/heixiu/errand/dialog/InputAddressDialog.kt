package com.heixiu.errand.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.heixiu.errand.R
import com.heixiu.errand.databinding.InputAddressDialogBinding
import kotlinx.android.synthetic.main.fragment_content.*

/**
 * Created by YuanGang on 2018/5/8.
 */
class InputAddressDialog(context: Context, contentFragment: OnAddressConfirm, type: String) : Dialog(context, R.style.CustomDialogStyle) {

    var addressConfirm = contentFragment
    var type = type
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

        binding.inputAddressEt.setThreshold(1)

        binding.inputAddressEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(binding.inputAddressEt.text)) {
                    binding.cancel.text = "取消"
                } else {
                    initData()
                    binding.cancel.text = "确认"
                }
            }
        })


        binding.cancel.setOnClickListener({
            if ("取消" == binding.cancel.text) {
            } else if ("确认" == binding.cancel.text) {
                addressConfirm.addressConfirm(binding.inputAddressEt.text.toString(), type)
            }
            dismiss()
        })

        initData()
    }

    var mSuggestionSearch: SuggestionSearch? = null

    fun initData() {
        mSuggestionSearch = SuggestionSearch.newInstance()
        mSuggestionSearch?.setOnGetSuggestionResultListener {
            if (it == null || it.getAllSuggestions() == null) {
                Log.i("result: ", "未找到相关结果\n")
            } else {
                var resl: List<SuggestionResult.SuggestionInfo> = it.getAllSuggestions();
                for (suggestionInfo in resl) {
                    Log.i("result: ", "city" + suggestionInfo.city + " dis " + suggestionInfo.district + "key " + suggestionInfo.key);

                }

            }
            //获取在线建议检索结果
        }
    }

    fun searchAddress() {
    }

    interface OnAddressConfirm {
        fun addressConfirm(address: String, type: String)
    }
}