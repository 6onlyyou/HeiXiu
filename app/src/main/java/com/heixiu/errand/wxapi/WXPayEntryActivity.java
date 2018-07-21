package com.heixiu.errand.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fushuaige.common.utils.ToastUtils;
import com.heixiu.errand.base.AppConstant;
import com.heixiu.errand.bean.PaySuccessEventEntity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/12/11.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        api = WXAPIFactory.createWXAPI(this, AppConstant.WX_ID, false);
        api.registerApp(AppConstant.WX_ID);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                ToastUtils.showShort("支付成功");
                PaySuccessEventEntity paySuccessEventEntity = new PaySuccessEventEntity();
                EventBus.getDefault().post(paySuccessEventEntity);
            } else {
                ToastUtils.showShort("支付失败, 请重试");
            }
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}
