package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.Button;

import com.heixiu.errand.MVP.Community.entity.DynamicEntity;
import com.heixiu.errand.R;
import com.heixiu.errand.net.OrderInfo;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class MyIssuedAdapter extends BaseQuickAdapter<OrderInfo> {

    public MyIssuedAdapter(int layoutResId, List<OrderInfo> data) {
        super(layoutResId, data);
    }

    public MyIssuedAdapter(List<OrderInfo> data) {
        super(data);
    }

    public MyIssuedAdapter(View contentView, List<OrderInfo> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.Tv_startingPart,item.getSendAddress()).setText(R.id.Tv_endingPart,item.getReceiveAddress());
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
