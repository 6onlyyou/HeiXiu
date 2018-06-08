package com.heixiu.errand.MVP.Message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_personal_page.*
import kotlinx.android.synthetic.main.fragment_message.*

class PersonalPageActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_personal_page)
        initTitle("个人信息", R.color.colorAccent, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn) { finishWithAnim() }
    }

    override fun findViewById() {
        personal_compile.setOnClickListener {
            startActivity(EditProfileActivity::class.java)
        }
    }

    override fun setListener() {

    }

    override fun processLogic() {
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryMyMessage(SPUtil.getString("userid"),"")).subscribe({
            page_nickname.text = it.userInfo.nickName
            page_sign.text = it.userInfo.sign
            page_briday.text = it.userInfo.birthday
            page_fans.text = it.userFanCounts.toString()
            page_attention.text = it.userFollowsCount.toString()
            page_orderInfoPublishCount.text = "发单任务数："+it.orderInfoPublishCount.toString()
            page_orderInfoReceiveCount.text = "接单数："+it.orderInfoReceiveCount.toString()

            if (it.userInfo.sex.equals("男")){
                Glide.with(this)
                        .load(R.mipmap.ic_launcher)
                        .crossFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(page_sex);
            }else{
                Glide.with(this)
                        .load(R.mipmap.page_woman)
                        .crossFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(page_sex);
            }


        }) { throwable ->
            ToastUtils.showShort(throwable.message)
        }
    }
}
