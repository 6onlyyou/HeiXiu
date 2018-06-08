package com.heixiu.errand.MVP.Message

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.base.AppConstant
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.MyAddressInfo
import com.heixiu.errand.dialog.CommomDialog
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_personal_address.*
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.graphics.Color.parseColor
import android.R.attr.textColor
import android.graphics.Color
import com.lljjcoder.style.citylist.Toast.ToastUtils.showLongToast
import android.databinding.adapters.TextViewBindingAdapter.setText
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citypickerview.CityPickerView






class PersonalAddressActivity : BaseActivity()  {

    var mCityPickerView = CityPickerView()
    private var mCountyId = ""
    private var mEditorEntity: MyAddressInfo? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_personal_address)
        mCityPickerView.init(this);
    }

    override fun findViewById() {

        initTitle("编辑地址", R.color.colorAccent, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn) { finishWithAnim() }

        mTitle.setTv_Right("提交", R.color.white, View.OnClickListener {
            val name = personal_address_name.getText().toString()
            val phone = personal_address_phone.getText().toString()
            val cityId = mCountyId
            val city = personal_address_city_tv.getText().toString()
            val detail = personal_address_detail.getText().toString()
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(city) ||
                    TextUtils.isEmpty(detail)) {
                ToastUtils.showShort("请补全信息重试")
                return@OnClickListener
            }
            if (!checkPhone(phone)) {
                ToastUtils.showShort("请输入正确手机号")
                return@OnClickListener
            }

            val entity = MyAddressInfo()
            entity.setReceiveName(name)
            entity.receiveNum=phone
            entity.area=city
            entity.detail=detail
            if (AppConstant.ADD.equals(mEditorEntity!!.getEditType())) {
                dell_address_ll.setVisibility(View.GONE)
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().createAddress(SPUtil.getString("userid"),name,phone,city,"",detail,0)).subscribe({

                    ToastUtils.showShort(it)
                    setResult(AppConstant.ADDRESS_EDIT_RESULT)
                    finishWithAnim()
                },{
                    ToastUtils.showLong(it.message)
                })
//                personalAddressPresenter.addAddress(entity)
            } else if (AppConstant.EDIT.equals(mEditorEntity!!.getEditType())) {
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().updateAddress(SPUtil.getString("userid"),mEditorEntity!!.id.toString(),name,phone,city,"",detail,0)).subscribe({
                    ToastUtils.showShort(it)
                    setResult(AppConstant.ADDRESS_EDIT_RESULT)
                    finishWithAnim()
                },{
                    ToastUtils.showLong(it.message)
                })
//                entity.setId(mEditorEntity.getAddressEntity().getId())
//                personalAddressPresenter.editAddress(entity)
            }
        })
            dell_address_ll.setOnClickListener(View.OnClickListener {
            //公共弹框调用
            if (mEditorEntity!!.id == 0) {
                ToastUtils.showLong("没有可以删除的地址")
                return@OnClickListener
            }
            CommomDialog(this, R.style.dialog, "确认删除该地址么？", object : CommomDialog.OnCloseListener {
               override fun onClick(dialog: Dialog, confirm: Boolean) {
                    if (confirm) {
                        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().removeAddress(SPUtil.getString("userid"),mEditorEntity!!.id.toString())).subscribe({
                            ToastUtils.showShort(it)
                            setResult(AppConstant.ADDRESS_EDIT_RESULT)
                            finishWithAnim()
                        },{
                            ToastUtils.showLong(it.message)
                        })
//                        deleteAddress(mEditorEntity.getAddressEntity().getId())
                        dialog.dismiss()
                    }
                }
            })
                    .setTitle("").show()
        })
        mEditorEntity = intent.getSerializableExtra("data") as MyAddressInfo
        if (AppConstant.ADD.equals(mEditorEntity!!.getEditType())) {

        } else if (AppConstant.EDIT.equals(mEditorEntity!!.getEditType())) {
            val entity = mEditorEntity
            personal_address_name.setText(entity!!.receiveName)
            personal_address_name.setSelection(entity!!.receiveName.length)
            personal_address_phone.setText(entity!!.receiveNum)
            if (entity!!.receiveName.length <= 10) {
                personal_address_phone.setSelection(entity!!.receiveNum.length) // 将光标移至文字末尾
            }
            personal_address_city_tv.setText(entity.area)
            personal_address_detail.setText(mEditorEntity!!.detail)
            personal_address_detail.setSelection(entity.detail.length)

//            mCountyId = "" + entity.area().getId()
        }

    }

    private fun selectAddress() {
        val cityConfig = CityConfig.Builder().title("选择城市")//标题
                .build()
        mCityPickerView.setConfig(cityConfig)
        mCityPickerView.setOnCityItemClickListener(object : OnCityItemClickListener() {
        override    fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                val sb = StringBuilder()
                if (province != null) {
                    sb.append(province.name + "," )
                }

                if (city != null) {
                    sb.append(city!!.getName() + "," )
                }

                if (district != null) {
                    sb.append(district.name + "," )
                }

            personal_address_city_tv.setText("" + sb.toString())

            }

            override fun onCancel() {
//                ToastUtils.showLongToast(this@PersonalAddressActivity, "已取消")
            }
        })
        mCityPickerView.showCityPicker()
    }
    override fun setListener() {
        personal_address_city.setOnClickListener{
            selectAddress()
        }
    }

    override fun processLogic() {

    }


}
