package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.ListCommentInfoBean;
import com.heixiu.errand.bean.MyFansBean;
import com.heixiu.errand.utils.SPUtil;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class CommentAdapter extends BaseQuickAdapter<ListCommentInfoBean> {
    List<ListCommentInfoBean> listCommentInfoBeans;
    public CommentAdapter(int layoutResId, List<ListCommentInfoBean> data) {
        super(layoutResId, data);
    }

    public CommentAdapter(List<ListCommentInfoBean> data) {
        super(R.layout.comment_item,data);
        listCommentInfoBeans = data;
    }

    public CommentAdapter(View contentView, List<ListCommentInfoBean> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListCommentInfoBean item) {
        helper.setText(R.id.comment_nickname,item.getNickName()).setText(R.id.comment_content,item.getContent());
//        //Glide加载图片  并且支持gif动图
        Glide.with(mContext)
                .load(item.getUserImg())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into((ImageView) helper.getView(R.id.comment_head));

    }

    public void addData(int position,String content) {
        ListCommentInfoBean listCommentInfoBean = new ListCommentInfoBean();
        listCommentInfoBean.setContent(content);
        listCommentInfoBean.setUserImg(SPUtil.getString("headurl"));
        listCommentInfoBean.setNickName(SPUtil.getString("nickname"));
//      在list中添加数据，并通知条目加入一条
        listCommentInfoBeans.add(position, listCommentInfoBean);
        //添加动画
        notifyItemInserted(position);
    }

}
