package com.heixiu.errand.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fushuaige.common.utils.ToastUtils;
import com.heixiu.errand.MVP.Message.OtherPersonalPageActivity;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.MyAttentionBean;
import com.heixiu.errand.net.RetrofitFactory;
import com.heixiu.errand.net.RxUtils;
import com.heixiu.errand.utils.SPUtil;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class AttentionAdapter extends BaseQuickAdapter<MyAttentionBean> {

    public AttentionAdapter(int layoutResId, List<MyAttentionBean> data) {
        super(layoutResId, data);
    }

    public AttentionAdapter(List<MyAttentionBean> data) {
        super(R.layout.fans_item, data);
    }

    public AttentionAdapter(View contentView, List<MyAttentionBean> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyAttentionBean item) {

        helper.setText(R.id.fans_nickname, item.getNickName());
        final Button fans_attention = (Button) helper.getView(R.id.fans_attention);
        fans_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().addFollow(SPUtil.getString("userid"), item.getFollowId())).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (fans_attention.getText().equals("已关注")) {
                            ToastUtils.showLong("取消成功");
                            fans_attention.setText("关注");
                        } else {
                            ToastUtils.showLong("关注成功");
                            fans_attention.setText("已关注");
                        }
                    }
                });

            }
        });

//        //Glide加载图片  并且支持gif动图
        Glide.with(mContext)
                .load(item.getUserImg())
                .crossFade()
                .placeholder(R.mipmap.defaulthead)
                .into((ImageView) helper.getView(R.id.fans_img));
        ImageView fans_img = (ImageView) helper.getView(R.id.fans_img);
        fans_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("stype", "1");
                intent.putExtra("friendid", item.getUserId());
                intent.setClass(mContext, OtherPersonalPageActivity.class);
                mContext.startActivity(intent);
            }
        });
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
