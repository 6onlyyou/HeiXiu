package com.heixiu.errand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heixiu.errand.R;
import com.heixiu.errand.net.OrderInfo;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2018/4/9.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    public List<OrderInfo> data = new ArrayList<>();
    Context mContext;
    OnItemClick onItemClick;


    public HomeAdapter(@Nullable Context context) {
        this.mContext = context;
    }

    public void setData(List<OrderInfo> data) {
        data.clear();
        this.data.addAll(data);
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
        if (false) {
            OrderInfo orderInfo = data.get(i);
//            viewHolder.startLocationTv.setText(orderInfo.getReceiveAddress());
//            viewHolder.endLocationTv.setText(orderInfo.getSendAddress());
//            viewHolder.type.setText(orderInfo.getName());
//            viewHolder.weight.setText(orderInfo.getWeight());
        }
    }

    @Override
    public int getItemCount() {
        return /*data.size()*/ 10;
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
//        @BindView(R.id.distance)
//        TextView distance;
//        @BindView(R.id.detail)
//        TextView detail;
//        @BindView(R.id.start_location_tv)
//        TextView startLocationTv;
//        @BindView(R.id.end_location_tv)
//        TextView endLocationTv;
//        @BindView(R.id.type)
//        TextView type;
//        @BindView(R.id.weight)
//        TextView weight;
//        @BindView(R.id.confirmOrder)
//        ImageView confirmOrder;

        public ViewHolder(View itemView) {
            super(itemView);
//            detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClick.onDetailClick(getLayoutPosition());
//                }
//            });
//            confirmOrder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClick.onConfirmOrderClick(getLayoutPosition());
//                }
//            });
        }
    }
}
