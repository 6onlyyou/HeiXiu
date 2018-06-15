package com.heixiu.errand.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.heixiu.errand.MVP.Community.VideoInfoActivity;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.MyFansBean;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;
import com.xiaochao.lcrapiddeveloplibrary.Video.JCVideoPlayerStandard;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class FansAdapter extends BaseQuickAdapter<MyFansBean> {

    public FansAdapter(int layoutResId, List<MyFansBean> data) {
        super(layoutResId, data);
    }

    public FansAdapter(List<MyFansBean> data) {
        super(R.layout.fans_item,data);
    }

    public FansAdapter(View contentView, List<MyFansBean> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFansBean item) {

        helper.setText(R.id.fans_nickname,item.getUserName());
//        //Glide加载图片  并且支持gif动图
        Glide.with(mContext)
                .load(item.getUserImg())
                .crossFade()
                .placeholder(R.mipmap.defaulthead)
                .into((ImageView) helper.getView(R.id.fans_img));
//        //对视频的赋值 添加视频播放地址(使用原地址  .mp4之类的  这个要注意)和标题
//        if(item.getViodeoUrl().equals("")||item.getViodeoUrl()==null){
//            helper.getView(R.id.video_list_item_playr).setVisibility(View.GONE);
//        }else {
//            ((JCVideoPlayerStandard) helper.getView(R.id.video_list_item_playr)).setUp(item.getViodeoUrl(), item.getTitle());
//            helper.getView(R.id.video_list_item_playr).setVisibility(View.VISIBLE);
//        }
//        Glide.with(mContext)
//                .load(item.getPictureUrl())
//                .crossFade()
//                .placeholder(R.mipmap.ic_launcher)
//                .into((((JCVideoPlayerStandard) helper.getView(R.id.video_list_item_playr)).thumbImageView));
//        LinearLayout linearLayout;
//        linearLayout = (LinearLayout) helper.getView(R.id.Ll_into);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, VideoInfoActivity.class);
//                mContext.startActivity(intent);
//            }
//        });

    }
}
