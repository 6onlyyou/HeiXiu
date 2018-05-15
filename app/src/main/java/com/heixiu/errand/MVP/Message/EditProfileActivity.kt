package com.heixiu.errand.MVP.Message

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.EditText
import android.widget.Toast
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.feezu.liuli.timeselector.TimeSelector
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_edit_profile)
    }

    override fun findViewById() {
        profile_sex.setOnClickListener {
            change_sex()
        }
        profile_birdday.setOnClickListener {
            val d = Date()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val timeSelector = TimeSelector(this, TimeSelector.ResultHandler { time ->
                Toast.makeText(this, time, Toast.LENGTH_SHORT).show()
            }, "1888-01-01 00:00", sdf.format(d))
            timeSelector.setIsLoop(false);//设置不循环,true循环
            timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
            timeSelector.show();
        }
        profile_sign.setOnClickListener {
            val inputServer = EditText(this)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("设置签名").setView(inputServer)
                    .setNegativeButton("取消", null)
            builder.setPositiveButton("确定") { dialog, which ->

            }
            builder.show()
        }
        profile_attestation
    }

    override fun setListener() {

    }

    override fun processLogic() {
    }


    fun change_sex() {
        val builder = AlertDialog.Builder(this) //定义一个AlertDialog
        val strarr = arrayOf("男", "女")
        builder.setItems(strarr, object : DialogInterface.OnClickListener {
          override  fun onClick(arg0: DialogInterface, arg1: Int) {
                var sex = "2"
                // 自动生成的方法存根
                if (arg1 == 0) {//男
                    profile_sex.setText("男")
                    sex = "1"
                } else {//女
                    profile_sex.setText("女")
                    sex = "2"
                }
            }
        })
        builder.show()
    }

}
