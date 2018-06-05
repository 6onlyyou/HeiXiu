package com.heixiu.errand.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.fushuaige.common.utils.ToastUtils;
import com.google.gson.Gson;
import com.heixiu.errand.MVP.Message.PersonalAddressActivity;
import com.heixiu.errand.R;
import com.heixiu.errand.base.AppConstant;
import com.heixiu.errand.base.BaseActivity;
import com.heixiu.errand.bean.MyAddressInfo;
import com.heixiu.errand.net.RetrofitFactory;
import com.heixiu.errand.net.RxUtils;
import com.heixiu.errand.utils.SPUtil;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/26.
 * 地址列表adapter
 */

public class DeliveryAddressAdapter extends BaseQuickAdapter<MyAddressInfo> {

    protected ProgressDialog dialog;
    private AlertDialog mDialog;
    private int selectedPosition = 0;// 选中的位置

    public DeliveryAddressAdapter(@Nullable List<MyAddressInfo> data) {
        super(R.layout.item_delivery_address, data);
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyAddressInfo item) {
//        TextView chkTv = helper.getView(R.id.delivery_address_chk_text);
        CheckBox chk = helper.getView(R.id.delivery_address_chk);
        LinearLayout deliveryAddressLl = helper.getView(R.id.delivery_address_default_ll);
        helper.setText(R.id.delivery_address_receiver, item.getReceiveName())
                .setText(R.id.delivery_address_phone, "("+item.getReceiveNum()+")")
                .setText(R.id.delivery_address_address, item.getArea()
                        + item.getDetail()
                        + item.getStreet()
                        )
                .setOnClickListener(R.id.delivery_address_edit_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAddressInfo editorEntity = item;
                        editorEntity.setEditType(AppConstant.EDIT);
                        ((BaseActivity) mContext).startActivity(PersonalAddressActivity.class, editorEntity);
                    }
                })
//                .setOnClickListener(R.id.delivery_address_delete_ll, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //公共弹框调用
//                        new CommomDialog(mContext, R.style.dialog, "确认删除该地址么？", new CommomDialog.OnCloseListener() {
//                            @Override
//                            public void onClick(Dialog dialog, boolean confirm) {
//                                if (confirm) {
//                                    deleteAddress(helper.getLayoutPosition());
//                                    dialog.dismiss();
//                                }
//                            }
//                        })
//                                .setTitle("").show();
//                    }
//                })
                .setOnClickListener(R.id.delivery_address_default_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (helper.getLayoutPosition() == selectedPosition) {
                            return;
                        } else {
                            setSelectedPosition(helper.getLayoutPosition());
                            setDefaultAddress(helper.getLayoutPosition());
                        }
                    }
                });
        if (selectedPosition == helper.getLayoutPosition()) {
            chk.setChecked(true);
//            chkTv.setText("默认地址");
//            chkTv.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//            item.setDefaultAddress(true);
        } else {
            chk.setChecked(false);
//            chkTv.setText("设为默认");
//            chkTv.setTextColor(mContext.getResources().getColor(R.color.gray_text));
//            item.setDefaultAddress(false);
        }
    }

    private void setDefaultAddress(final int position) {
        showLoading();
        List<Integer> list = new ArrayList<>();
        list.add(mData.get(position).getId());
        MyAddressInfo myAddressInfo = mData.get(position);
        for (MyAddressInfo entity : mData) {
            if (!list.contains(entity.getId())) {
                list.add(entity.getId());
            }
        }
        RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().updateAddress(SPUtil.getString("userid"),myAddressInfo.getId()+"",myAddressInfo.getReceiveName(),myAddressInfo.getReceiveNum(),myAddressInfo.getArea(),"",myAddressInfo.getDetail(),1)).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                dismissLoading();
                ToastUtils.showShort("设置默认地址成功");
            }
        });

    }

//    private void deleteAddress(final int position) {
//        showLoading();
//        RetrofitFactory.INSTANCE.getRetrofit(AddressService.class).deleteAddress(mData.get(position).getId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ResponseBean>() {
//                    @Override
//                    public void accept(ResponseBean responseBean) throws Exception {
//                        dismissLoading();
//                        remove(position);
//                        if (position == selectedPosition && mData.size() >= 1 && position < mData.size() - 1) {
//                            setDefaultAddress(position);
//                            setSelectedPosition(position);
//                        } else if (position == selectedPosition && mData.size() >= 1 && position == mData.size()) {
//                            setDefaultAddress(position - 1);
//                            setSelectedPosition(position - 1);
//                        }
//                        if (position == 0) {
//                            setSelectedPosition(0);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        dismissLoading();
//                    }
//                });
//    }

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(mContext, R.style.ProgressDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在提交...");
        dialog.show();
    }

    public void showLoading(String message) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(mContext, R.style.ProgressDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
