package com.heixiu.errand.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fushuaige.common.widget.TitleBar;
import com.heixiu.errand.R;

import java.io.Serializable;


/**
 * Created by XY on 2016/9/11.
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected TitleBar mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = createView(inflater, container);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    public void initTitle(int id, View view, int bgcolor, int titlecolor) {
        if (mTitle == null) {
            mTitle = new TitleBar(getActivity(), view, id, bgcolor, titlecolor);
        }
    }
    public void initTitle(String name, int bgcolor, int titlecolor) {
        if (mTitle == null) {
            mTitle = new TitleBar(getActivity(), mRootView.findViewById(android.R.id.content), name, bgcolor, titlecolor);
        }
    }
    /**
     * initTitle:初始化标题. <br/>
     *
     * @param name 标题文本
     */
    public void initTitle(String name, View view, int bgcolor, int titlecolor) {
        if (mTitle == null) {
            mTitle = new TitleBar(getActivity(), view, name, bgcolor, titlecolor);
        }
    }
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void initView();
    public void startActivity(Class<?> activity) {
        startActivity(activity, null);
    }

    public void startActivity(Class<?> activity, Object data) {
        Intent intent = new Intent();
        intent.setClass(getContext(), activity);
        if (data != null)
            intent.putExtra("data", (Serializable) data);
        startActivity(intent);
    }
}
