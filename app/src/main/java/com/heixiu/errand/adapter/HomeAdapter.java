package com.heixiu.errand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.heixiu.errand.MyApplication.MyApplication;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.OrderInfo;

import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2018/4/9.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private static final String TAG = "HomeAdapter";
    public List<OrderInfo> data = new ArrayList<>();
    Context mContext;
    OnItemClick onItemClick;

    public HomeAdapter(@Nullable Context context) {
        this.mContext = context;
    }

    public void setData(List<OrderInfo> datas) {
        data.clear();
        data.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(OrderInfo data) {
        this.data.add(data);
        notifyDataSetChanged();
    }

    public void deleteData(OrderInfo data) {
        this.data.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        OrderInfo orderInfo = data.get(i);

        if (orderInfo.getRecieveMapAdress() == null) {
            orderInfo.setRecieveMapAdress("");
        }

        if (orderInfo.getSendMapAdress() == null) {
            orderInfo.setSendMapAdress("");
        }


        viewHolder.startLocationTv.setText(orderInfo.getReceiveAddress() + orderInfo.getRecieveMapAdress());
        viewHolder.endLocationTv.setText(orderInfo.getSendAddress() + orderInfo.getSendMapAdress());
        viewHolder.type.setText(orderInfo.getName());
        viewHolder.weight.setText(orderInfo.getWeight() + "公斤");

        if (MyApplication.getInstance().localLat == 0) {
            viewHolder.distance.setText("暂时未获取到您的定位");
        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            String distanceFormat = "";
            double distance = DistanceUtil.getDistance(
                    new LatLng(MyApplication.getInstance().localLat, MyApplication.getInstance().localLong)
                    , new LatLng(orderInfo.getOriginsLatitude(), orderInfo.getOriginsLongitude()));
            Log.i(TAG, "接单列表: Lat = " + orderInfo.getOriginsLatitude()
                    + "Long = " + orderInfo.getOriginsLongitude()
            );

            Log.i(TAG, "num: "+orderInfo.getOrderNum() + "   描述"+ orderInfo.getDescription());

            if ((distance) >= 1000 && distance < 1000000000) {
                distanceFormat = df.format(distance / 1000);
                viewHolder.distance.setText("距您" + distanceFormat + "公里");
            } else if (distance < 1000) {
                distanceFormat = df.format(distance);
                viewHolder.distance.setText("距您" + distanceFormat + "米");
            } else {

            }

        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onDetailClick(int position);

        void onConfirmOrderClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView distance;
        TextView detail;
        TextView startLocationTv;
        TextView endLocationTv;
        TextView type;
        TextView weight;
        ImageView confirmOrder;

        public ViewHolder(View itemView) {
            super(itemView);
            distance = itemView.findViewById(R.id.distance);
            detail = itemView.findViewById(R.id.detail);
            startLocationTv = itemView.findViewById(R.id.start_location_tv);
            endLocationTv = itemView.findViewById(R.id.end_location_tv);
            type = itemView.findViewById(R.id.type);
            weight = itemView.findViewById(R.id.weight);
            confirmOrder = itemView.findViewById(R.id.confirmOrder);
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onDetailClick(getLayoutPosition());
                }
            });
            confirmOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onConfirmOrderClick(getLayoutPosition());
                }
            });
        }
    }
}
