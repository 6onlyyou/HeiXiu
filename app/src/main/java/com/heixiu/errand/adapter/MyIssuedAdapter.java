package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.Button;

import com.heixiu.errand.MVP.Community.entity.DynamicEntity;
import com.heixiu.errand.R;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class MyIssuedAdapter extends BaseQuickAdapter<DynamicEntity> {

    public MyIssuedAdapter(int layoutResId, List<DynamicEntity> data) {
        super(layoutResId, data);
    }

    public MyIssuedAdapter(List<DynamicEntity> data) {
        super(data);
    }

    public MyIssuedAdapter(View contentView, List<DynamicEntity> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicEntity item) {
//        helper.setText(R.id.video_list_item_text_title,item.getTitle()).setText(R.id.video_list_item_text_context,item.getIntroduction()).setText(R.id.Iv_communityNickName,item.getNickname()).setText(R.id.Tv_communityPraise,item.getPraise()).setText(R.id.Tv_communitySend,item.getComment());
        //Glide加载图片  并且支持gif动图
        Button btnDetails;
        btnDetails =  helper.getView(R.id.Bt_details);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, VideoInfoActivity.class);
//                mContext.startActivity(intent);
            }
        });

    }
}
