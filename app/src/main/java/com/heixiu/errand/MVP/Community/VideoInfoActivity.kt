package com.heixiu.errand.MVP.Community

import android.view.View
import com.heixiu.errand.MVP.Community.callback.DialogFragmentDataCallback
import com.heixiu.errand.MVP.Community.fragment.CommentFragment
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.fushuaige.common.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_video_info.*

class VideoInfoActivity : BaseActivity(), DialogFragmentDataCallback {
    override fun addDanmakuToView(content: String?) {
        ToastUtils.showLong("评论"+content);
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_video_info);
        initTitle("视频", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
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
