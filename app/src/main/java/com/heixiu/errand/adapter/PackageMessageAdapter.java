package com.heixiu.errand.adapter;

import android.view.View;

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
public class PackageMessageAdapter extends BaseQuickAdapter<PackageInformationBean.TracesBean> {

    public PackageMessageAdapter(List<PackageInformationBean.TracesBean> data) {
        super(R.layout.item_package_message, data);
    }

    public PackageMessageAdapter(View contentView, List<PackageInformationBean.TracesBean> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PackageInformationBean.TracesBean item) {
        helper.setText(R.id.time, item.getAcceptTime())
                .setText(R.id.address, item.getAcceptStation());
        if (helper.getLayoutPosition() == 0) {
            helper.setImageResource(R.id.point, R.mipmap.ic_point_main);
        } else {
            helper.setImageResource(R.id.point, R.mipmap.ic_point_gray);
        }
    }
}

