package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.Button;

import com.heixiu.errand.MVP.Home.OrderDetailActivity;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.OrderInfo;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class MyTaskAdapter extends BaseQuickAdapter<OrderInfo> {

    public MyTaskAdapter(int layoutResId, List<OrderInfo> data) {
        super(layoutResId, data);
    }

    public MyTaskAdapter(List<OrderInfo> data) {
        super(R.layout.item_task, data);
    }

    public MyTaskAdapter(View contentView, List<OrderInfo> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderInfo item) {
        helper.setText(R.id.Tv_startingPart, item.getSendAddress()).setText(R.id.Tv_endingPart, item.getReceiveAddress());
        //Glide加载图片  并且支持gif动图

        Button btnDetails;
        btnDetails = helper.getView(R.id.Bt_details);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, OrderDetailActivity.class);
//                intent.setClass(mContext, OrderDetailActivity.class);
//                mContext.startActivity(intent);
                OrderDetailActivity.Companion.startSelf(mContext, item);
            }
        });

    }
}
