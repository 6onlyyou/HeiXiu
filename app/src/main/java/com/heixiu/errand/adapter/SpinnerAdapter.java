package com.heixiu.errand.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heixiu.errand.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanGang on 2018/5/24.
 */

public class SpinnerAdapter extends BaseAdapter {
    List<String> datas = new ArrayList<>();
    Context mContext;

    public SpinnerAdapter(Context context) {
        this.mContext = context;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null);
            hodler.mTextView = (TextView) convertView.findViewById(R.id.kuaidiName);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

        hodler.mTextView.setText(datas.get(position));
        if (datas.get(position).contains("市")) {
            hodler.mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
        return convertView;
    }

//    @Override
//    public View getDropDownView(int position, View convertView,
//                                ViewGroup parent) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_layout, null);
//        TextView label = (TextView) view.findViewById(R.id.spinner_item_label);
//        ImageView check = (ImageView) view.findViewById(R.id.spinner_item_checked_image);
////        label.setText(gradeList.get(position));
//
//        return view;
//    }


    private static class ViewHodler {
        TextView mTextView;

    }
}