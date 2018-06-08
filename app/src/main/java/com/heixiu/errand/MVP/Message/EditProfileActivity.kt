package com.heixiu.errand.MVP.Message

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Seting.AuthenticationActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.feezu.liuli.timeselector.TimeSelector
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_edit_profile)
    }

    override fun findViewById() {
        initTitle("编辑资料", R.color.colorPrimary, R.color.white)

        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })
        mTitle.setTv_Right("保存", R.color.colorPrimary,View.OnClickListener{
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().editData(SPUtil.getString("userid"),profile_name.text.toString(),profile_sex.toString(),profile_sign.toString(),profile_birdday.toString())).subscribe({
                ToastUtils.showLong("保存成功")
                finishWithAlpha()
            },{
                ToastUtils.showLong(it.message)
            })
    })
    getUserMessage()
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
        profile_attestation.setOnClickListener {
            startActivity(AutonymActivity::class.java)
        }
        profile_bind.setOnClickListener {
            startActivity(AliBindActivity::class.java)
        }
    }
    fun getUserMessage(){
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().selectDataById(SPUtil.getString("userid"))).subscribe({
            profile_nickname.text = it.userInfo.nickName
            profile_signshow.text = it.userInfo.sign
            profile_name.setText(it.userInfo.nickName)
            profile_sex.setText(it.userInfo.sex)
            profile_birdday.setText(it.userInfo.birthday)
            profile_sign.setText(it.userInfo.sign)
            if(it.dbSubAccount.cardStatus.equals("0")){
                profile_attestation.setText("未绑定")
            }else{
                profile_attestation.setText("已绑定")
            }
            if(it.dbSubAccount.zfbStatus.equals("0")){
                profile_bind.setText("未绑定")
            }else{
                profile_bind.setText("已绑定")
            }


            Glide.with(this)
                    .load(it.userInfo.userImg)
                    .crossFade()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(profile_hard);

        },{
            ToastUtils.showLong(it.message)
        })

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
