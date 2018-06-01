package com.heixiu.errand.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by admin on 2017/4/8.
 */

public class WindowSize {
    public static int getWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth()-222;
        return width;
    }

}