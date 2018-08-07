package com.heixiu.errand.MVP.Message.wallet

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Message.AliBindActivity
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.bean.QueryMyIncomeBean
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_earnings.*

class EarningsActivity : BaseActivity()  , View.OnClickListener{
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.chaeak_wx->
                if(bindStute==1) {
                    if(queryMyIncomeBean!!.amountAll==null){
                        ToastUtils.showLong("还没有可以提现的金额")
                    }else {
                        startActivity(WxWithdrawActivity::class.java, queryMyIncomeBean)
                    }
                }else{
                    startActivity(AliBindActivity::class.java)

                }
            R.id.choose_ali->
                if(bindStute==1) {
                if(queryMyIncomeBean!!.amountAll==null){
                    ToastUtils.showLong("还没有可以提现的金额")
                }else {
                    startActivity(WithdrawActivity::class.java, queryMyIncomeBean)
                }
            }else{
                startActivity(AliBindActivity::class.java)

            }
        }
        dialog!!.dismiss();
    }

    var bindStute:Int = 0
  var   queryMyIncomeBean: QueryMyIncomeBean? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_earnings)
        initTitle("我的收益", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })


    }
    private var inflate: View? = null
    private var choosePhoto: TextView? = null
    private var takePhoto: TextView? = null
    private var dialog: Dialog? = null
    override fun findViewById() {
        earn_goearn.setOnClickListener {
            dialog =  Dialog(this,R.style.ActionSheetDialogStyle);
            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.withdraw_head, null);
            //初始化控件
            choosePhoto =  inflate!!.findViewById(R.id.chaeak_wx);
            takePhoto = inflate!!.findViewById(R.id.choose_ali);
            choosePhoto!!.setOnClickListener(this);
            takePhoto!!.setOnClickListener(this);
            //将布局设置给Dialog
            dialog!!.setContentView(inflate);
            //获取当前Activity所在的窗体
            var dialogWindow: Window = dialog!!.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity( Gravity.BOTTOM);
            //获得窗体的属性
            var lp: WindowManager.LayoutParams = dialogWindow.getAttributes();
            lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog!!.show();//显示对话框

        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

    override fun onResume() {
        super.onResume()
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().queryMyIncome(SPUtil.getString("userid"))).subscribe({
           if(it.amountAll==null){
               earn_menoy.text = "￥0"
           }else{
               earn_menoy.text = "￥"+it.amountAvailable.toString()
           }
            queryMyIncomeBean = it

            if(it.zfbStatus==1){
                bindStute = 1
            }
        },{
            ToastUtils.showLong(it.message)
        })
    }
}
