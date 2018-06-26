package com.heixiu.errand.MVP.Community

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Community.callback.DialogFragmentDataCallback
import com.heixiu.errand.MVP.Community.fragment.CommentFragment
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.R
import com.heixiu.errand.adapter.CommentAdapter
import com.heixiu.errand.adapter.MyGridViewAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.PublishInfoDetail
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_video_info.*

class VideoInfoActivity : BaseActivity(), DialogFragmentDataCallback {
    var publishId: String = ""
    var publishInfoDetail: PublishInfoDetail? = null
    var commentAdapter: CommentAdapter? = null
    override fun addDanmakuToView(content: String?) {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit()
                .createComment(SPUtil.getString("userid"), publishInfoDetail!!.userId, content, publishInfoDetail!!.publishId.toString() + ""))
                .subscribe { s ->
                    ToastUtils.showLong(s)
                    commentAdapter!!.addData(publishInfoDetail!!.listCommentInfo.size, content)
                }
    }


    override fun loadViewLayout() {
        setContentView(R.layout.activity_video_info);
        initTitle("动态详情", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        publishId = intent.getStringExtra("publishId").toString()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().showOnePublishInfoDetail(publishId, SPUtil.getString("userid"))).subscribe({
            publishInfoDetail = it
            info_nickname.text = publishInfoDetail!!.nickName.toString()
            info_content.text = publishInfoDetail!!.content
            info_praise.text = publishInfoDetail!!.admireCount.toString()
            info_comment.text = publishInfoDetail!!.commentCount.toString()
            Rv_comment.setLayoutManager(LinearLayoutManager(this))
                if(publishInfoDetail!!.admireStatus==0){
                    val drawableLeft = resources.getDrawable(
                            R.mipmap.nopraise)
                    info_praise.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
                }else{
                    val drawableLeft = resources.getDrawable(R.mipmap.praise)
                    info_praise.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
                }
            if(publishInfoDetail!!.followStatus==0){
                video_info_attention.setText("关注")
            }else{
                video_info_attention.setText("已关注")
            }

//        如果Item高度固定  增加该属性能够提高效率
            Rv_comment.setHasFixedSize(true)
//        设置适配器
            commentAdapter = CommentAdapter(publishInfoDetail!!.listCommentInfo)
            //设置加载动画
            commentAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
            //设置是否自动加载以及加载个数
            //将适配器添加到RecyclerView
            Rv_comment.setAdapter(commentAdapter)
            //设置自动加载监听
            if (publishInfoDetail!!.getType() == "0") {
                gridView.setVisibility(View.VISIBLE)
                val num = publishInfoDetail!!.contentImgList.size//获取当前的图片数目
                var col = 1//默认列数
                if (num == 1) {
                    gridView.setNumColumns(1)
                    col = 1
                } else if (num == 2 || num == 4) {
                    gridView.setNumColumns(2)
                    col = 2
                } else {
                    gridView.setNumColumns(3)
                    col = 3
                }

//            gridView.setNumColumns(3)
                gridView.setAdapter(MyGridViewAdapter(this, num, col, publishInfoDetail!!.contentImgList))

//            gridView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
//                override fun onItemClick(arg0: AdapterView<*>, arg1: View,
//                                         position: Int, arg3: Long) {
//                                    DlgForBigPhto(data.get(position).toString());
//                                    Toast.makeText(context, "dongtai" + position, Toast.LENGTH_SHORT).show();
//                }
//            })
            } else {
                //对视频的赋值 添加视频播放地址(使用原地址  .mp4之类的  这个要注意)和标题
                if (publishInfoDetail!!.getContentVideo() == "" || publishInfoDetail!!.getContentVideo() == null) {
                    video_list_item_playr.setVisibility(View.GONE)
                } else {
                    video_list_item_playr.setUp(publishInfoDetail!!.getContentVideo(), publishInfoDetail!!.getContent())
                    video_list_item_playr.setVisibility(View.VISIBLE)
                }
            }
            Glide.with(mContext)
                    .load(publishInfoDetail!!.getUserImg())
                    .crossFade()
                    .placeholder(R.mipmap.defaulthead)
                    .into(info_headimg)
        }, {
            ToastUtils.showLong(it.message)
        })


        video_info_attention.setOnClickListener {
            if (SPUtil.getString("userid") == publishInfoDetail!!.getUserId()) {
                ToastUtils.showLong("不能关注自己")
                return@setOnClickListener
            }
            RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().addFollow(SPUtil.getString("userid"), publishInfoDetail!!.userId)).subscribe({
                if(video_info_attention.text.toString().equals("已关注")){
                    video_info_attention.setText("关注")
                }else{
                    video_info_attention.setText("已关注")
                }
            }, {
                ToastUtils.showLong(it.message)
            })
        }
        Et_comment.setOnClickListener {
            CommentFragment.getInstance().show(fragmentManager, "danmakuFragment")
        }
        info_praise.setOnClickListener {
            if (SPUtil.getString("userid") == "" || SPUtil.getString("userid") == "1") {
                val intent = Intent(mContext, LoginActivity::class.java)
                mContext.startActivity(intent)
            } else {
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().userAdmire(publishInfoDetail!!.userId, publishInfoDetail!!.getPublishId() + "",SPUtil.getString("userid"))).subscribe({ s ->
                    if(publishInfoDetail!!.admireStatus==0){
                        val drawableLeft = resources.getDrawable(
                                R.mipmap.praise)
                        info_praise.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
                    }else{
                        val drawableLeft = resources.getDrawable(
                                R.mipmap.nopraise)
                        info_praise.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
                    }
                })
                { throwable -> ToastUtils.showLong(throwable.message) }
            }
        }
    }

    override fun findViewById() {

    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
