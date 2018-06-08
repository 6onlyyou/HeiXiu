package com.heixiu.errand.adapter;

import android.view.View;

import com.heixiu.errand.MVP.Community.entity.DynamicEntity;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class WithdrawalRecordAdapter extends BaseQuickAdapter<DynamicEntity> {

    public WithdrawalRecordAdapter(int layoutResId, List<DynamicEntity> data) {
        super(layoutResId, data);
    }

    public WithdrawalRecordAdapter(List<DynamicEntity> data) {
        super(data);
    }

    public WithdrawalRecordAdapter(View contentView, List<DynamicEntity> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicEntity item) {
//        helper.setText(R.id.video_list_item_text_context,item.getIntroduction()).setText(R.id.Iv_communityNickName,item.getNickname()).setText(R.id.Tv_communityPraise,item.getPraise());
//        //Glide加载图片  并且支持gif动图
//        Glide.with(mContext)
//                .load(item.getHeadurl())
//                .crossFade()
//                .placeholder(R.mipmap.ic_launcher)
//                .into((ImageView) helper.getView(R.id.Iv_communityHead));
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
