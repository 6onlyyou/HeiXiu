package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.heixiu.errand.MVP.Home.StartOrderDetailActivity;
import com.heixiu.errand.MyApplication.MyApplication;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.OrderInfo;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.text.DecimalFormat;
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
        String stuts = null;
        helper.setText(R.id.Tv_startingPart, item.getSendAddress()).setText(R.id.Tv_endingPart, item.getReceiveAddress());
        //Glide加载图片  并且支持gif动图
        helper.setText(R.id.Tv_Time, "送达时间" + item.getSendTime());
        switch (item.getOrderStatus()) {
            case "0":
                stuts = "刚创建";
                break;
            case "1":
                stuts = "已接单";
                break;
            case "2":
                stuts = "已取货";
                break;
            case "3":
                stuts = "已送达";
                break;
            case "4":
                stuts = "完成";
                break;
            default:
                stuts = "问题订单";
        }
        Button btnDetails;
        helper.setText(R.id.Tv_proceed, stuts);
        if (stuts.equals("完成")) {
            helper.setText(R.id.Tv_unOrder, "订单已完成");
        }

        btnDetails = helper.getView(R.id.Bt_details);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, OrderDetailActivity.class);
//                intent.setClass(mContext, OrderDetailActivity.class);
//                mContext.startActivity(StartOrderDetailActivity.class,item);
                StartOrderDetailActivity.Companion.startSelf(mContext, item);
//                OrderDetailActivity.Companion.startSelf(mContext, item);
            }
        });
        if (MyApplication.getInstance().localLat == 0) {
            helper.setText(R.id.Tv_deliveryTime, "暂时未获取到您的定位");

        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            String distanceFormat = "";
            double distance = DistanceUtil.getDistance(
                    new LatLng(MyApplication.getInstance().localLat, MyApplication.getInstance().localLong)
                    , new LatLng(item.getOriginsLatitude(), item.getOriginsLongitude()));
            if ((distance) >= 1000 && distance < 1000000000) {
                distanceFormat = df.format(distance / 1000);
                helper.setText(R.id.Tv_deliveryTime, "距您" + distanceFormat + "公里");
            } else if (distance < 1000) {
                distanceFormat = df.format(distance);
                helper.setText(R.id.Tv_deliveryTime, "距您" + distanceFormat + "米");
            } else {

            }
        }
    }
}
