package com.heixiu.errand.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import com.heixiu.errand.R;
import com.heixiu.errand.dialog.DialogShowPic;
import com.heixiu.errand.dialog.DialogShowPicP;
import com.heixiu.errand.utils.WindowSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2017/4/8.
 */

public class MyGridViewAdapter extends BaseAdapter {
    Context context;
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
        img.setPadding(16, 0, 16, 0);

        int width = WindowSize.getWidth(context) - 50;// 获取屏幕宽度
        Log.i("tag", "width" + width);
        int height = 0;
        width = width / col;// 对当前的列数进行设置imgView的宽度
        height = width;
        img.setLayoutParams(new AbsListView.LayoutParams(width, height));
        Glide.with(img.getContext())
                .load(list.get(position))
                .crossFade()
                .placeholder(R.mipmap.defaulthead)
                .into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlgForBigPhto(list.get(position));
            }
        });
        return img;
    }

    public ImageView dialog6IvPic;

    private void DlgForBigPhto(final String url) {
        DialogShowPic pDialogMy = new DialogShowPic(context);
        pDialogMy.SetStyle(R.style.myDialogTheme1);
        pDialogMy.InitDialog(new DialogShowPicP() {
            public LinearLayout dialog6LlBckgrnd;

            @Override
            public void SetDialogView(Dialog pDialog, final DialogShowPic pDialogMy) {
                pDialog.setContentView(R.layout.show_pic);
                dialog6LlBckgrnd = (LinearLayout) pDialog.findViewById(R.id.dialog6LlBckgrnd);
                dialog6LlBckgrnd.setOnClickListener(pDialogMy);
                dialog6IvPic = (ImageView) pDialog.findViewById(R.id.dialog6IvPic);
                dialog6IvPic.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setItems(new String[]{"保存图片"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog6IvPic.setDrawingCacheEnabled(true);
                                Bitmap imageBitmap = dialog6IvPic.getDrawingCache();
                                if (imageBitmap != null) {
                                    saveImageToGallery(context, imageBitmap);
                                }
                            }
                        });
                        builder.show();
                        return true;
                    }
                });
                dialog6IvPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pDialogMy.Destroy();
                    }
                });
                Glide.with(dialog6IvPic.getContext())
                        .load(url)
                        .crossFade()
                        .into(dialog6IvPic);
            }

            @Override
            public void SetOnClickListener(View v, DialogShowPic pDialogMy1) {
                if (v == dialog6LlBckgrnd) {
                    pDialogMy1.Destroy();
                }
            }

            @Override
            public void SetOnKeyListener(int keyCode, KeyEvent event) {
            }
        });
        pDialogMy.Show();
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
    }
}