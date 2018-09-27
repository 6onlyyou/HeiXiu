package com.heixiu.errand.MVP.Contentt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.Event.PublishParamsChangeEvent
import com.heixiu.errand.R
import com.heixiu.errand.adapter.PackageTypeAdapter
import com.heixiu.errand.utils.RxBus
import kotlinx.android.synthetic.main.activity_package_type.*
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

class PackageTypeActivity : AppCompatActivity() {

    var name = ""
    var receiverName = ""
    var receiverNum = ""
    var deliverNums = ""
    var descriptions = ""

    private lateinit var adapter: PackageTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_type)

        package_type_Rv!!.layoutManager = GridLayoutManager(this, 4)
        adapter = PackageTypeAdapter(this)
        adapter.typeItemClick = PackageTypeAdapter.PackageTypeItemClick {
            name = adapter.data[it]
        }


        if (!TextUtils.isEmpty(ContentFragment.receiverName)) {
            receiverName = ContentFragment.receiverName
            receiveName.setText(ContentFragment.receiverName)
        }

        if (!TextUtils.isEmpty(ContentFragment.receiverNum)) {
            receiverNum = ContentFragment.receiverNum
            receiveNum.setText(ContentFragment.receiverNum)
        }
        if (!TextUtils.isEmpty(ContentFragment.courierNum)) {
            deliverNums = ContentFragment.courierNum
            deliverNum.setText(ContentFragment.courierNum)
        }
        if (!TextUtils.isEmpty(ContentFragment.descriptions)) {
            descriptions = ContentFragment.descriptions
            description.setText(ContentFragment.descriptions)
        }

        receiveName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                receiverName = receiveName.text.toString()
            }
        })
        receiveNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                receiverNum = receiveNum.text.toString()
            }

        })
        description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                descriptions = description.text.toString()
            }

        })
        deliverNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                deliverNums = deliverNum.text.toString()
            }
        })

        package_type_Rv!!.adapter = adapter

        confirm.setOnClickListener({
            if (!isChinaPhoneLegal(receiverNum)) {
                ToastUtils.showShort("检查电话号码，重新输入")
            } else {
                ContentFragment.packageType = name
                ContentFragment.descriptions = descriptions
                ContentFragment.receiverName = receiverName
                ContentFragment.receiverNum = receiverNum
                ContentFragment.courierNum = "1221"
                finish()
            }
        })
    }

    @Throws(PatternSyntaxException::class)
    fun isChinaPhoneLegal(str: String): Boolean {
        val regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(str)
        return m.matches()
    }

    private fun checkHasNullMessage(): Boolean {
        if (TextUtils.isEmpty(receiverName) || TextUtils.isEmpty(receiverNum)) {
            return true
        }
        return false
    }

    companion object {
        fun startSelf(context: Context) {
            val intent = Intent(context, PackageTypeActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().post(PublishParamsChangeEvent())
    }
}
