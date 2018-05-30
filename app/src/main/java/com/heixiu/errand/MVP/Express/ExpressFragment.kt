package com.heixiu.errand.MVP.Express


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
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
//            https://m.kuaidi100.com/index_all.html?type=quanfengkuaidi&postid=123456#result
            false
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
