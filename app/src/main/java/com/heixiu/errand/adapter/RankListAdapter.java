package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.ImageView;

import com.fushuaige.common.utils.GlideUtil;
import com.heixiu.errand.MVP.Community.entity.DynamicEntity;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.PackageInformationBean;
import com.heixiu.errand.bean.RankBean;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class RankListAdapter extends BaseQuickAdapter<RankBean> {


    public RankListAdapter(int layoutResId, List<RankBean> data) {
        super(layoutResId, data);
    }

    public RankListAdapter(List<RankBean> data) {
        super(R.layout.item_rank,data);
    }

    public RankListAdapter(View contentView, List<RankBean> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankBean item) {
        helper.setText(R.id.rank_ranking,item.getRank()+"");
        helper.setText(R.id.rank_name,item.getUserId()+"");
        helper.setText(R.id.rank_order,item.getReceiveCount()+"");
        helper.setText(R.id.rank_getmoney,item.getAmountDay()+"");
        GlideUtil.load(mContext, item.getUserImg(), (ImageView) helper.getView(R.id.rank_img));
    }
}
