package com.heixiu.errand.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fushuaige.common.utils.GlideUtil;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.PublishInfoDetail;
import com.heixiu.errand.dialog.DialogShowPic;
import com.heixiu.errand.dialog.DialogShowPicP;
import com.heixiu.errand.utils.WindowSize;

import java.util.List;

/**
 * Created by admin on 2017/4/8.
 */

public class MyGridViewAdapter extends BaseAdapter {
    Context context;
//    public DynamicBean dynamicBean;
    int num;
    int col;
    List<String> list;

    public MyGridViewAdapter(Context context, int num, int col, List<String> list) {
        this.context = context;
        this.num = num;
        this.col = col;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return num;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView img = new ImageView(context);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setPadding(16,0,16,0);
        int width = WindowSize.getWidth(context)-50;// 获取屏幕宽度
        Log.i("tag", "width" + width);
        int height = 0;
        width = width / col;// 对当前的列数进行设置imgView的宽度
        height = width;
        for(int i=0;i<list.size();i++){
            img.setLayoutParams(new AbsListView.LayoutParams(width, height));
            Glide.with(img.getContext())
                    .load(list.get(i))
                    .crossFade()
                    .placeholder(R.mipmap.defaulthead)
                    .into(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DlgForBigPhto(list.get(position));
                }
            });
        }

        return img;
    }
    private void DlgForBigPhto(final String url) {
        DialogShowPic pDialogMy = new DialogShowPic(context);
        pDialogMy.SetStyle(R.style.myDialogTheme1);
        pDialogMy.InitDialog(new DialogShowPicP() {
            public LinearLayout dialog6LlBckgrnd;
            public ImageView dialog6IvPic;
            @Override
            public void SetDialogView(Dialog pDialog, DialogShowPic pDialogMy) {
                pDialog.setContentView(R.layout.show_pic);
                dialog6LlBckgrnd = (LinearLayout)pDialog.findViewById(R.id.dialog6LlBckgrnd);
                dialog6LlBckgrnd.setOnClickListener(pDialogMy);
                dialog6IvPic = (ImageView)pDialog.findViewById(R.id.dialog6IvPic);
                Glide.with(dialog6IvPic.getContext())
                        .load(url)
                        .crossFade()
                        .into(dialog6IvPic);
//				WindowManager wm = getWindowManager();
//				int nScreenWidth = wm.getDefaultDisplay().getWidth();
//				int nWidth = nScreenWidth;
//				int nHeight = nWidth;
//				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(nWidth, nHeight);
//				pDialogView.dialog6IvPic.setLayoutParams(params);
            }
            @Override
            public void SetOnClickListener(View v, DialogShowPic pDialogMy) {
                if (v == dialog6LlBckgrnd) {
                    pDialogMy.Destroy();
                }
            }
            @Override
            public void SetOnKeyListener(int keyCode, KeyEvent event) {
            }
        });
        pDialogMy.Show();
    }

}