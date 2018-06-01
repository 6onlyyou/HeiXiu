package com.heixiu.errand.MVP.Community.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by admin on 2017/4/8.
 */

public class PictureGridView extends GridView {
    public PictureGridView(Context context) {
        super(context);
    }

    public PictureGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //重写onMeasure里面方法，使界面撑到最大
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}