package com.heixiu.errand.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import com.heixiu.errand.R

/**
 * Created by YuanGang on 2018/4/18.
 */
open class BottomDialog(context: Context) : Dialog(context, R.style.CustomDialogStyle) {
    init {
        val dialogWindow = window
        dialogWindow.setGravity(Gravity.BOTTOM)
        dialogWindow.decorView.setPadding(0, 0, 0, 0)
        val lp = dialogWindow.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialogWindow.attributes = lp
    }
}