package com.example.app.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.app.MVP.Community.entity.DynamicEntity;
import com.example.app.R;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;
import com.xiaochao.lcrapiddeveloplibrary.Video.JCVideoPlayerStandard;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class CommounityAdapter extends BaseQuickAdapter<DynamicEntity> {

    public CommounityAdapter(int layoutResId, List<DynamicEntity> data) {
        super(layoutResId, data);
    }

    public CommounityAdapter(List<DynamicEntity> data) {
        super(data);
    }

    public CommounityAdapter(View contentView, List<DynamicEntity> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicEntity item) {
        helper.setText(R.id.video_list_item_text_title,item.getTitle()).setText(R.id.video_list_item_text_context,item.getIntroduction());
        //Glide加载图片  并且支持gif动图
        Glide.with(mContext)
                .load(item.getPictureUrl())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into((ImageView) helper.getView(R.id.video_list_item_image));
        //对视频的赋值 添加视频播放地址(使用原地址  .mp4之类的  这个要注意)和标题
        if(item.getViodeoUrl().equals("")||item.getViodeoUrl()==null){
            helper.getView(R.id.video_list_item_playr).setVisibility(View.GONE);
        }else {
            ((JCVideoPlayerStandard) helper.getView(R.id.video_list_item_playr)).setUp(item.getViodeoUrl(), item.getTitle());
            helper.getView(R.id.video_list_item_playr).setVisibility(View.INVISIBLE);
        }
        Glide.with(mContext)
                .load(item.getPictureUrl())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into((((JCVideoPlayerStandard) helper.getView(R.id.video_list_item_playr)).thumbImageView));
    }
}
