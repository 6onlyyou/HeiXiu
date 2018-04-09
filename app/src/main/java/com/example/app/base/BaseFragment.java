package com.example.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by XY on 2016/9/11.
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {

    protected View mRootView;

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

    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void initView();
}
