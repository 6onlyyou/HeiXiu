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
import com.heixiu.errand.R
import com.heixiu.errand.adapter.PackageTypeAdapter
import kotlinx.android.synthetic.main.activity_package_type.*

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

        back.setOnClickListener({
            if (checkHasNullMessage()) {
                ToastUtils.showLong("信息填写不完整，请补全信息")
            } else {
                finish()
            }
        })

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
            if (checkHasNullMessage()) {
                ToastUtils.showLong("信息填写不完整，请补全信息")
            } else {
                ContentFragment.packageType = name
                ContentFragment.descriptions = descriptions
                ContentFragment.receiverName = receiverName
                ContentFragment.receiverNum = receiverNum
                ContentFragment.courierNum = deliverNums
            }
            finish()
        })
    }

    private fun checkHasNullMessage(): Boolean {
        if (TextUtils.isEmpty(receiverName) || TextUtils.isEmpty(receiverNum) || TextUtils.isEmpty(descriptions)) {
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

    override fun onBackPressed() {
        if (checkHasNullMessage()) {
            ToastUtils.showLong("信息填写不完整，请补全信息")
        } else {
            super.onBackPressed()
        }

    }
}
