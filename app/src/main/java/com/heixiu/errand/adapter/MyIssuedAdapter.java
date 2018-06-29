package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.Button;

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
public class MyIssuedAdapter extends BaseQuickAdapter<OrderInfo> {

    public MyIssuedAdapter(int layoutResId, List<OrderInfo> data) {
        super(layoutResId, data);
    }

    public MyIssuedAdapter(List<OrderInfo> data) {

        super(R.layout.item_issued,data);
    }

    public MyIssuedAdapter(View contentView, List<OrderInfo> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.Tv_startingPart,item.getSendAddress()).setText(R.id.Tv_endingPart,item.getReceiveAddress());
        //Glide加载图片  并且支持gif动图
        helper.setText(R.id.Tv_deliveryTime,"送达时间："+item.getSendTime());
        Button btnDetails;
        btnDetails =  helper.getView(R.id.Bt_details);
        String stuts = null;
        switch (item.getOrderStatus()){
            case "0":
                stuts ="刚创建";
                break;
            case "1":
                stuts ="已接单";
                break;
            case "2":
                stuts ="已取货";
                break;
            case "3":
                stuts ="已送达";
                break;
            case "4":
                stuts ="完成";
                break;
        }
        helper.setText(R.id.Tv_proceed, stuts);
        if(stuts.equals("完成")){
            helper.setText(R.id.Tv_unOrder, "订单已完成");
        }

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
}
