package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.fushuaige.common.utils.ToastUtils;
import com.heixiu.errand.MVP.Community.entity.DynamicEntity;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.CouponTicketBean;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class TicketAdapter extends BaseQuickAdapter<CouponTicketBean> {

    public TicketAdapter( List<CouponTicketBean> data) {
        super(R.layout.item_ticket, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CouponTicketBean item) {
        helper.setOnClickListener(R.id.use_ticket, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //事件处理
            }
        });
    }
}
