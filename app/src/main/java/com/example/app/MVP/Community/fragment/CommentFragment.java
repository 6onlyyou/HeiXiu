package com.example.app.MVP.Community.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.MVP.Community.callback.DialogFragmentDataCallback;
import com.example.app.R;
import com.fushuaige.common.utils.ToastUtils;


public class CommentFragment extends DialogFragment {

    public static CommentFragment instance;
    EditText mCommentEt;
    TextView mCommentBtn;
    private Dialog mDialog;
    private Handler mHandler;
    private DialogFragmentDataCallback mCallback;

    public static CommentFragment getInstance() {
        if (instance == null) {
            instance = new CommentFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof DialogFragmentDataCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 DialogFragmentDataCallback 接口");
        }
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_fragment_danmaku);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        mCallback = (DialogFragmentDataCallback) getActivity();
        mCommentEt = mDialog.findViewById(R.id.comment_et);
        mCommentBtn = mDialog.findViewById(R.id.comment_btn);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mCommentEt.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort("内容不能为空");
                    return;
                }
                mCallback.addDanmakuToView(content);
                dismiss();
            }
        });
        mHandler = new Handler();
        setSoftKeyboard();

        return mDialog;
    }

    private void setSoftKeyboard() {
        mCommentEt.setFocusable(true);
        mCommentEt.setFocusableInTouchMode(true);
        mCommentEt.requestFocus();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) mCommentEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(mCommentEt, 0);
            }
        }, 100L);

    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
            //
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if (!getActivity().isFinishing())
            super.dismissAllowingStateLoss();
    }
}
