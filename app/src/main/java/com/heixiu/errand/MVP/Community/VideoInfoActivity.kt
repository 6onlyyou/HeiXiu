package com.heixiu.errand.MVP.Community

import android.view.View
import com.heixiu.errand.MVP.Community.callback.DialogFragmentDataCallback
import com.heixiu.errand.MVP.Community.fragment.CommentFragment
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.bean.PublishInfoDetail
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_video_info.*

class VideoInfoActivity : BaseActivity(), DialogFragmentDataCallback {
    var publishInfoDetail: PublishInfoDetail? = null
    override fun addDanmakuToView(content: String?) {
        ToastUtils.showLong("评论"+content);
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_video_info);
        initTitle("视频", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        publishInfoDetail   = intent.getSerializableExtra("data") as PublishInfoDetail
        video_info_attention.setOnClickListener {
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addFollow(SPUtil.getString("userid"), publishInfoDetail!!.userId)).subscribe({
                ToastUtils.showLong("关注成功")
                finishWithAlpha()
            },{
                ToastUtils.showLong(it.message)
            })
        }
        Et_comment.setOnClickListener{
            CommentFragment.getInstance().show(fragmentManager, "danmakuFragment")
        }

    }
    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
