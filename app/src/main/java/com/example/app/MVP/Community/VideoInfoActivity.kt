package com.example.app.MVP.Community

import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.example.app.MVP.Community.fragment.CommentFragment
import com.example.app.R
import com.example.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_video_info.*

class VideoInfoActivity : BaseActivity() {
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
