package com.heixiu.errand.adapter;

import android.view.View;
import android.widget.TextView;

import com.heixiu.errand.R;
import com.heixiu.errand.bean.MyReciecedOrderBean;
import com.heixiu.errand.bean.WithdrawBean;
import com.heixiu.errand.utils.TimeUtils;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Description:
 * Data：2018/4/12-10:44
 * Author: fushuaige
 */
public class withdrawalAdapter extends BaseQuickAdapter<WithdrawBean> {

    public withdrawalAdapter(int layoutResId, List<WithdrawBean> data) {
        super(layoutResId, data);
    }

    public withdrawalAdapter(List<WithdrawBean> data) {
        super(R.layout.item_withdrawalrecord,data);
    }

    public withdrawalAdapter(View contentView, List<WithdrawBean> data) {
        super(contentView, data);
    }
    public  String  payType(String type ){
        if(type.equals("0")){
            return "支付宝";
        }else {
            return "微信";
        }
    }
    @Override
    protected void convert(BaseViewHolder helper, WithdrawBean item) {
        TextView withdraw_account = (TextView) helper.getView(R.id.withdraw_account);
        withdraw_account.setVisibility(View.VISIBLE);
        helper.setText(R.id.withdraw_numbers,"提现渠道："+payType(item.getType())).setText(R.id.withdraw_time, item.getAmount().toString()+"元").setText(R.id.withdraw_pay,item.getStatus()).setText(R.id.withdraw_account,item.getAccount());

    }
}
