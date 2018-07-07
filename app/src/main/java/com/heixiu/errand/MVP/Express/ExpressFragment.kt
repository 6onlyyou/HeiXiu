package com.heixiu.errand.MVP.Express


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.R
import com.heixiu.errand.adapter.SpinnerAdapter
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.utils.SPUtil
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_express.*


/**
 * A simple [Fragment] subclass.
 */
class ExpressFragment : BaseFragment() {
    override fun initView() {

    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_express, container, false)
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    var choosePosition = -1;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        zxing.setOnTouchListener(View.OnTouchListener { v, event ->
            // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
            val drawable = zxing.compoundDrawables[2] ?: return@OnTouchListener false
            //如果不是按下事件，不再处理
            if (event.action !== MotionEvent.ACTION_UP)
                return@OnTouchListener false
            if (event.x > (zxing.width - zxing.paddingRight - drawable.intrinsicWidth)) {
                val intent = Intent(context, CaptureActivity::class.java)
                startActivityForResult(intent, 111)
            }
            false
        })

        var spinnerAdapter = SpinnerAdapter(context)
        spinnerAdapter.setDatas(resources.getStringArray(R.array.package_name).toList())
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                ToastUtils.showShort("请选择")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                choosePosition = position
            }
        }

        zxing.setText("3920060496244")

        search.setOnClickListener({
            startSearch()
        })

        search_history.setOnClickListener({
            if (TextUtils.isEmpty(SPUtil.getString("userid"))) {
                startActivity(Intent(context, LoginActivity::class.java))
                ToastUtils.showShort("您尚未登录")
            } else {
                startActivity(Intent(context, ExpressHistoryActivity::class.java))
            }
        })
    }

    private fun startSearch() {
        if (choosePosition == -1) {
            ToastUtils.showShort("请选择快递公司名称")
            return
        }
        if (TextUtils.isEmpty(zxing.text)) {
            ToastUtils.showShort("未输入快递单号")
            return
        }


        if (TextUtils.isEmpty(SPUtil.getString("userid"))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }

        RetrofitFactory.getRetrofit().queryLogisticalInformation(
                resources.getStringArray(R.array.package_id)[choosePosition],
                zxing.text.toString(),
                SPUtil.getString("userid")
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {
                        var intent = Intent(context, ExpressMessageActivity::class.java)
                        intent.putExtra("data", it)
                        intent.putExtra("name", resources.getStringArray(R.array.package_name)[choosePosition])
                        startActivity(intent)
                    } else {
                        ToastUtils.showShort("暂无信息")
                    }
                }, {
                    ToastUtils.showShort(it.message)
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                var bundle: Bundle = data.extras
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    var result = bundle.getString(CodeUtils.RESULT_STRING)
                    Toast.makeText(context, "解析结果:" + result, Toast.LENGTH_LONG).show()
                    zxing.setText(result)
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
