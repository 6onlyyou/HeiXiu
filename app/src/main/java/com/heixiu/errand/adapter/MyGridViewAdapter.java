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
    List<PublishInfoDetail> list;

    public MyGridViewAdapter(Context context, int num, int col, List<PublishInfoDetail> list) {
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
        int width = WindowSize.getWidth(context)-50;// 获取屏幕宽度
        Log.i("tag", "width" + width);
        int height = 0;
        width = width / col;// 对当前的列数进行设置imgView的宽度
        height = width;
        for(int i=0;i<list.size();i++){
            img.setLayoutParams(new AbsListView.LayoutParams(width, height));
            GlideUtil.load(img.getContext(), "https://img-blog.csdn.net/20170428175617391?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveWVjaGFvYQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center", img);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    DlgForBigPhto(dynamicBean.getImageUrl()[position].toString());
                    Toast.makeText(context, "mygridviewadapter" + position, Toast.LENGTH_SHORT).show();
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
                GlideUtil.load(dialog6IvPic.getContext(), "https://img-blog.csdn.net/20170428175617391?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveWVjaGFvYQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center", dialog6IvPic);

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