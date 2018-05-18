package com.heixiu.errand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heixiu.errand.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by PVer on 2018/4/9.
 */

public class PackageTypeAdapter extends RecyclerView.Adapter<PackageTypeAdapter.ViewHolder> {

    public String[] data;
    public int selectPosition = -1;
    public PackageTypeItemClick typeItemClick;
    Context mContext;

    public PackageTypeAdapter(@Nullable Context context) {
        this.mContext = context;
        data = context.getResources().getStringArray(R.array.package_type);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_package_type, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (position == selectPosition) {
            viewHolder.packageTypeTv.setSelected(true);
        } else {
            viewHolder.packageTypeTv.setSelected(false);
        }
        viewHolder.packageTypeTv.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public interface PackageTypeItemClick {
        void click(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageTypeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            packageTypeTv = itemView.findViewById(R.id.package_type_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectPosition(getLayoutPosition());
                    if (typeItemClick != null) {
                        typeItemClick.click(getLayoutPosition());
                    }
                }
            });
        }
    }
}
