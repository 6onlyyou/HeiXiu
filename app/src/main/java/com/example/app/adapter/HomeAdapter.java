package com.example.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by PVer on 2018/4/9.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context mContext;

    public HomeAdapter(@Nullable Context context) {
        this.mContext = context;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
