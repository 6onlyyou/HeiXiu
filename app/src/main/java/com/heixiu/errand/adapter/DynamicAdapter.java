package com.heixiu.errand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heixiu.errand.R;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Description:
 * Data：2018/8/11-10:53
 * Author: fushuaige
 */
public class DynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MediaBean> infos ;
    private LayoutInflater inflater;
    public interface OnItemOnClickListener{
        void onItemOnClick(View view, int pos);
        void onItemLongOnClick(View view ,int pos);
    }
    private OnItemOnClickListener mOnItemOnClickListener;
    public void setOnItemClickListener(OnItemOnClickListener listener){
        this.mOnItemOnClickListener = listener;
    }
    public DynamicAdapter(Context mContext,List<MediaBean> infos){
        this.mContext = mContext;
        this.infos = infos;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.dynamic_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void removeItem(int pos){
        infos.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(mContext)
                .load(infos.get(position).getOriginalPath())
                .crossFade()
                .placeholder(R.mipmap.defaulthead)
                .into((ImageView)viewHolder.getTextView());
//        viewHolder.getTextView().set(infos.get(position));
        if(mOnItemOnClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemOnClickListener.onItemOnClick(holder.itemView,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemOnClickListener.onItemLongOnClick(holder.itemView,position);
                    return false;
                }
            });
        }

    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView dynamic_img;
        public ViewHolder(View itemView) {
            super(itemView);
            dynamic_img  = (ImageView) itemView.findViewById(R.id.dynamic_img);
        }
        public ImageView getTextView(){
            return dynamic_img;
        }
    }
    @Override
    public int getItemCount() {
        return infos.size();
    }
}
