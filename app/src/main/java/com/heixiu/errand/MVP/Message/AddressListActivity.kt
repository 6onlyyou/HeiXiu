package com.heixiu.errand.MVP.Message

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.DeliveryAddressAdapter
import com.heixiu.errand.adapter.MyIssuedAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_address_list.*
import java.util.ArrayList

class AddressListActivity : BaseActivity() {
    internal var myIssuedAdapter: DeliveryAddressAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_address_list)
    }

    override fun findViewById() {
        initTitle("收货地址", R.color.colorAccent, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn) { finishWithAnim() }

        address_rv.setLayoutManager(LinearLayoutManager(mContext))
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryListOfMyAddressInfos(SPUtil.getString("userid"))).subscribe({
            address_rv.setLayoutManager(LinearLayoutManager(this))
//        如果Item高度固定  增加该属性能够提高效率
            address_rv.setHasFixedSize(true)
//        设置适配器
            myIssuedAdapter = DeliveryAddressAdapter( it)
            //设置加载动画
            myIssuedAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
            //设置是否自动加载以及加载个数
            //将适配器添加到RecyclerView
            address_rv.setAdapter(myIssuedAdapter)
            //设置自动加载监听
            myIssuedAdapter = DeliveryAddressAdapter(it)
        },{
            ToastUtils.showLong(it.message)
        })
//        val list = ArrayList<AddressEntity>()
//        mAdapter = DeliveryAddressAdapter(list)
//        mDeliverAddressRv.setAdapter(mAdapter)

//        mIsSelectAddress = intent.getBooleanExtra(AppConstant.DATA, false)
//        mAdapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener() {
//            fun onItemClick(adapter: BaseQuickAdapter, view: View, position: Int) {
//                if (mIsSelectAddress) {
//                    val intent = Intent()
//                    intent.putExtra(AppConstant.DATA, mAdapter.getItem(position))
//                    setResult(AppConstant.ADDRESS_SELECT_RESULT, intent)
//                    finishWithAnim()
//                } else {
//                    val addressEntity = adapter.getItem(position) as AddressEntity
//                    val editorEntity = AddressEditorEntity()
//                    editorEntity.setFirstAdd(false)
//                    editorEntity.setEditType(AppConstant.EDIT)
//                    editorEntity.setAddressEntity(addressEntity)
//                    startActivity(PersonalAddressActivity::class.java, editorEntity)
//                }
//            }
//        })
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
