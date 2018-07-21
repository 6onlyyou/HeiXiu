package com.heixiu.errand.adapter;

import com.heixiu.errand.R;
import com.heixiu.errand.bean.ExpressHistoryBean;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class ExpressHistoryAdapter extends BaseQuickAdapter<ExpressHistoryBean.LogisticsRecordsBean> {

    public ExpressHistoryAdapter(List<ExpressHistoryBean.LogisticsRecordsBean> data) {
        super(R.layout.item_express_history, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ExpressHistoryBean.LogisticsRecordsBean item) {
        helper.setText(R.id.name, item.getLogisticsName());
        helper.setText(R.id.num, item.getLogisticsNum());
        if (item.getLogisticsStatus().equals("0")) {
            helper.setText(R.id.state, "没有物流轨迹");
        }
        if (item.getLogisticsStatus().equals("2")) {
            helper.setText(R.id.state, "配送中");
        }
        if (item.getLogisticsStatus().equals("3")) {
            helper.setText(R.id.state, "签收");
        }
        if (item.getLogisticsStatus().equals("4")) {
            helper.setText(R.id.state, "问题件");
        }
    }
}

