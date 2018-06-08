package com.heixiu.errand.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.heixiu.errand.MVP.Community.VideoInfoActivity;
import com.heixiu.errand.MVP.Community.entity.DynamicEntity;
import com.heixiu.errand.MVP.Community.widget.PictureGridView;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.PublishInfoDetail;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;
import com.xiaochao.lcrapiddeveloplibrary.Video.JCVideoPlayerStandard;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */

public class CommounityAdapter extends BaseQuickAdapter<PublishInfoDetail> {
    private List<PublishInfoDetail> datalist;
    public CommounityAdapter(int layoutResId, List<PublishInfoDetail> data) {
        super(layoutResId, data);
    }

    public CommounityAdapter(List<PublishInfoDetail> data) {
        super(data);
    }

    public CommounityAdapter(View contentView, List<PublishInfoDetail> data) {
        super(contentView, data);
        datalist = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, PublishInfoDetail item) {
//        PictureGridView gridview;
//        gridview = (PictureGridView) helper.getView(R.id.gridView);
//        int num =  datalist.size();//获取当前的图片数目
//        int col = 1;//默认列数
//        Log.i("tag", "num" + num);
//        if (num == 1) {
//            gridview.setNumColumns(1);
//            col = 1;
//        } else if (num == 2 || num == 4) {
//            gridview.setNumColumns(2);
//            col = 2;
//        } else {
//            gridview.setNumColumns(3);
//            col = 3;
//        }
//
//        gridview.setNumColumns(3);
//        gridview.setAdapter(new MyGridViewAdapter(mContext, num, col,datalist));
//
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1,
//                                    int position, long arg3) {
//
////                DlgForBigPhto(data.get(position).toString());
////                Toast.makeText(context, "dongtai" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
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
