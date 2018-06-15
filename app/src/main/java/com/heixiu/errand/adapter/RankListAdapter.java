package com.heixiu.errand.adapter;

import android.view.View;

import com.heixiu.errand.MVP.Community.entity.DynamicEntity;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.PackageInformationBean;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class RankListAdapter extends BaseQuickAdapter<DynamicEntity> {


    public RankListAdapter(int layoutResId, List<DynamicEntity> data) {
        super(layoutResId, data);
    }

    public RankListAdapter(List<DynamicEntity> data) {
        super(R.layout.item_rank,data);
    }

    public RankListAdapter(View contentView, List<DynamicEntity> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicEntity item) {

    }
}
