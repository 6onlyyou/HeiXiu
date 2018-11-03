package com.heixiu.errand.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.fushuaige.common.utils.ToastUtils;
import com.heixiu.errand.Event.PublishParamsChangeEvent;
import com.heixiu.errand.MVP.Contentt.ContentFragment;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.CouponTicketBean;
import com.heixiu.errand.utils.RxBus;
import com.heixiu.errand.utils.TimeUtils;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class TicketAdapter extends BaseQuickAdapter<CouponTicketBean> {

    public TicketAdapter(List<CouponTicketBean> data) {
        super(R.layout.item_ticket, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final CouponTicketBean item) {

        helper.setText(R.id.price, item.getCouponPrice() + "")
                .setText(R.id.description, "下单立减"+item.getCouponPrice()+"元");

        helper.setText(R.id.time, TimeUtils.millis2String(item.getStartTime(), TimeUtils.ARTICLE_FORMAT) + "--"
                + TimeUtils.millis2String(item.getEndTime(), TimeUtils.ARTICLE_FORMAT)
        );
        helper.setOnClickListener(R.id.use_ticket, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //事件处理

                RxBus.getDefault().post(item);
                ContentFragment.Companion.setTicketBean(item);

                ((Activity) mContext).finish();
            }
        });
    }
}
