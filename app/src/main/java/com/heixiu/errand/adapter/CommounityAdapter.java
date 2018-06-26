package com.heixiu.errand.adapter;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fushuaige.common.utils.ToastUtils;
import com.heixiu.errand.MVP.Community.VideoInfoActivity;
import com.heixiu.errand.MVP.Community.callback.DialogFragmentDataCallback;
import com.heixiu.errand.MVP.Community.fragment.CommentFragment;
import com.heixiu.errand.MVP.Community.widget.PictureGridView;
import com.heixiu.errand.MVP.Login.LoginActivity;
import com.heixiu.errand.MVP.Message.OtherPersonalPageActivity;
import com.heixiu.errand.MVP.Message.PersonalPageActivity;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.PubLishInfo;
import com.heixiu.errand.net.RetrofitFactory;
import com.heixiu.errand.net.RxUtils;
import com.heixiu.errand.utils.SPUtil;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;
import com.xiaochao.lcrapiddeveloplibrary.Video.JCVideoPlayerStandard;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */

public class CommounityAdapter extends BaseQuickAdapter<PubLishInfo>{

    FragmentManager fragmentManager;
    private List<PubLishInfo> datalist;
    private PubLishInfo publishInfoDetail;

    public CommounityAdapter(int layoutResId, List<PubLishInfo> data) {
        super(layoutResId, data);
    }

    public CommounityAdapter(List<PubLishInfo> data) {
        super(R.layout.community_item, data);
    }

    public CommounityAdapter(View contentView, List<PubLishInfo> data) {
        super(contentView, data);
        datalist = data;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, final PubLishInfo item) {
        publishInfoDetail = item;
        if (item.getType().equals("0")) {
            PictureGridView gridview;
            gridview = (PictureGridView) helper.getView(R.id.gridView);
            gridview.setVisibility(View.VISIBLE);
            int num = item.getContentImgList().size();//获取当前的图片数目
            int col = 1;//默认列数
            Log.i("tag", "num" + num);
            if (num == 1) {
                gridview.setNumColumns(1);
                col = 1;
            } else if (num == 2 || num == 4) {
                gridview.setNumColumns(2);
                col = 2;
            } else {
                gridview.setNumColumns(3);
                col = 3;
            }

            gridview.setNumColumns(3);
            gridview.setAdapter(new MyGridViewAdapter(mContext, num, col, item.getContentImgList()));

//            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> arg0, View arg1,
//                                        int position, long arg3) {
//
////                DlgForBigPhto(data.get(position).toString());
////                Toast.makeText(context, "dongtai" + position, Toast.LENGTH_SHORT).show();
//                }
//            });
        } else {
            //对视频的赋值 添加视频播放地址(使用原地址  .mp4之类的  这个要注意)和标题
            if (item.getContentVideo().equals("") || item.getContentVideo() == null) {
                helper.getView(R.id.video_list_item_playr).setVisibility(View.GONE);
            } else {
                ((JCVideoPlayerStandard) helper.getView(R.id.video_list_item_playr)).setUp(item.getContentVideo(), item.getContent());
                helper.getView(R.id.video_list_item_playr).setVisibility(View.VISIBLE);
            }
        }

        helper.setText(R.id.video_list_item_text_context, item.getContent()).setText(R.id.Iv_communityNickName, item.getNickName()).setText(R.id.Tv_communityPraise, item.getAdmireCount() + "");
        //Glide加载图片  并且支持gif动图
        Glide.with(mContext)
                .load(item.getUserImg())
                .crossFade()
                .placeholder(R.mipmap.defaulthead)
                .into((ImageView) helper.getView(R.id.Iv_communityHead));
        final Button  community_attention = (Button) helper.getView(R.id.community_attention);
        community_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPUtil.getString("userid").equals("")||SPUtil.getString("userid").equals("1")){

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                }else {
                    if(SPUtil.getString("userid").equals(item.getUserId())){
                        ToastUtils.showLong("不能关注自己");
                        return;
                    }
                    RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().addFollow(SPUtil.getString("userid"), item.getUserId())).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                           if(community_attention.getText().equals("已关注")){
                               ToastUtils.showLong("取消成功");
                               community_attention.setText("关注");
                           }else {
                               ToastUtils.showLong("关注成功");
                               community_attention.setText("已关注");
                           }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ToastUtils.showLong(throwable.getMessage());
                        }
                    });
                }
            }
        });
        final ImageView itemPraise  = helper.getView(R.id.item_praise);
        if(item.getAdmireStatus()==0){
            itemPraise.setImageResource(R.mipmap.nopraise);
        }else{
            itemPraise.setImageResource(R.mipmap.praise);
        }
        if(item.getFollowStatus()==0){
            community_attention.setText("关注");
        }else{
            community_attention.setText("已关注");
        }
        final EditText Et_comment = helper.getView(R.id.Et_comment);
        ImageView Iv_comment = helper.getView(R.id.comment_send);
        Iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPUtil.getString("userid").equals("")||SPUtil.getString("userid").equals("1")){
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }else {
                    RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().createComment(SPUtil.getString("userid"), item.getUserId(), Et_comment.getText().toString(), publishInfoDetail.getPublishId()+ "")).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            ToastUtils.showLong(s);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ToastUtils.showLong(throwable.getMessage());
                        }
                    });
                }
            }
        });
//        Et_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CommentFragment.getInstance().show(fragmentManager, "danmakuFragment");
//            }
//        });
        LinearLayout personal_inonclick = (LinearLayout) helper.getView(R.id.personal_inonclick);
        personal_inonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mContext instanceof PersonalPageActivity) {
                    return;
                }
                Intent intent = new Intent(mContext, OtherPersonalPageActivity.class);
                intent.putExtra("friendid", item.getUserId());
                mContext.startActivity(intent);
            }
        });
        LinearLayout community_prias = (LinearLayout) helper.getView(R.id.community_prias);
        community_prias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPUtil.getString("userid").equals("")||SPUtil.getString("userid").equals("1")){

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                }else {
                    RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().userAdmire(item.getUserId(), item.getPublishId() + "",SPUtil.getString("userid"))).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            if(item.getAdmireStatus()==0){
                                itemPraise.setImageResource(R.mipmap.praise);
                            }else{
                                itemPraise.setImageResource(R.mipmap.nopraise);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ToastUtils.showLong(throwable.getMessage());
                        }
                    });
                }
            }
        });
        LinearLayout linearLayout;
        linearLayout = (LinearLayout) helper.getView(R.id.Ll_into);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, VideoInfoActivity.class);
                intent.putExtra("publishId", item.getPublishId());
                mContext.startActivity(intent);
            }
        });

    }
}
