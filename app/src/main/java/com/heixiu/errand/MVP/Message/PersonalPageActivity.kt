package com.heixiu.errand.MVP.Message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.GlideUtil
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.CommounityAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.PubLishInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_personal_page.*
import kotlinx.android.synthetic.main.fragment_message.*
import java.text.SimpleDateFormat
import java.util.*

class PersonalPageActivity : BaseActivity() {
    internal var commounityAdapter: CommounityAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_personal_page)
        initTitle("个人信息", R.color.colorAccent, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn) { finishWithAnim() }
    }

    override fun findViewById() {
        rv_list.setLayoutManager(LinearLayoutManager(this))
        //如果Item高度固定  增加该属性能够提高效率
        rv_list.setHasFixedSize(true)
        //设置适配器
        val list = ArrayList<PubLishInfo>()
        commounityAdapter = CommounityAdapter(list,"","")
        personal_compile.setOnClickListener {
            startActivity(EditProfileActivity::class.java)
        }
    }

    override fun setListener() {
        page_fansonclick.setOnClickListener {
            startActivity(MyFansActivity::class.java)
        }
        page_ll_attention.setOnClickListener {
            startActivity(MyAttentionActivity::class.java)
        }
    }

    override fun processLogic() {
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryMyMessage(SPUtil.getString("userid"),null)).subscribe({
            page_nickname.text = it.userInfo.nickName
            page_sign.text = it.userInfo.sign
            page_briday.text = getAgeByBirthDay(it.userInfo.birthday).toString()+"岁"
            page_fans.text = it.userFanCounts.toString()
            page_getpraise.text = it.admireCount.toString()
            page_attention.text = it.userFollowsCount.toString()
            page_orderInfoPublishCount.text = "发单任务数："+it.orderInfoPublishCount.toString()
            page_orderInfoReceiveCount.text = "接单数："+it.orderInfoReceiveCount.toString()

            GlideUtil.load(this@PersonalPageActivity, it.userInfo.userImg.toString(),page_headimg)
            if (it.userInfo.sex.equals("男")){
                Glide.with(this)
                        .load(R.mipmap.icon_man)
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


            if(it.publishInfos.size>0){

                rv_list.setLayoutManager(LinearLayoutManager(this))
//        如果Item高度固定  增加该属性能够提高效率
                rv_list.setHasFixedSize(true)
//        设置适配器
                commounityAdapter = CommounityAdapter(it.publishInfos,it.userInfo.userImg,it.userInfo.nickName)
                //设置加载动画
                commounityAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                //设置是否自动加载以及加载个数
                commounityAdapter!!.openLoadMore(10,true);
                //将适配器添加到RecyclerView
                rv_list.setAdapter(commounityAdapter)
                //设置自动加载监听
            }
        }) { throwable ->
            ToastUtils.showShort(throwable.message)
        }
    }
    fun getAgeByBirthDay(birthDay: String?): Int {
        if (birthDay == null || birthDay.length < 4) {
            return 0
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        //得到当前的年份
        val cYear = sdf.format(Date()).substring(0, 4)
        val cMouth = sdf.format(Date()).substring(5, 7)
        val cDay = sdf.format(Date()).substring(8, 10)
        //得到生日年份
        val birth_Year = birthDay.substring(0, 4)
        val birth_Mouth = birthDay.substring(5, 7)
        val birth_Day = birthDay.substring(8, 10)
        var age = Integer.parseInt(cYear) - Integer.parseInt(birth_Year)
        if (Integer.parseInt(cMouth) - Integer.parseInt(birth_Mouth) < 0) {
            age = age - 1
        } else if (Integer.parseInt(cMouth) - Integer.parseInt(birth_Mouth) == 0) {
            if (Integer.parseInt(cDay) - Integer.parseInt(birth_Day) > 0) {
                age = age - 1
            } else {
                age = Integer.parseInt(cYear) - Integer.parseInt(birth_Year)
            }
        } else if (Integer.parseInt(cMouth) - Integer.parseInt(birth_Mouth) > 0) {
            age = Integer.parseInt(cYear) - Integer.parseInt(birth_Year)
        }


        return age
    }
}
