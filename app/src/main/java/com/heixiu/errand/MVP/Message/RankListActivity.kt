package com.heixiu.errand.MVP.Message

import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_rank_list.*

class RankListActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_rank_list)
        initTitle("排行榜", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        dollStateTb.addTab(dollStateTb.newTab().setText("平台排行").setTag("1"))
        dollStateTb.addTab(dollStateTb.newTab().setText("好友排行").setTag("2"))
        dollStateTb.isSelected = true
        dollStateTb.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.tag == "1") {
                    my_doll_rv.setLayoutManager(LinearLayoutManager(this@RankListActivity))
//                    my_doll_rv.setAdapter(mAdapter)
//                    mAdapter.setNewData(unInOrder)
//                    setSelect(false)
                }
                if (tab.tag == "2") {
                    my_doll_rv.setLayoutManager(LinearLayoutManager(this@RankListActivity))
//                    my_doll_rv.setAdapter(mOtherAdapter)
//                    mOtherAdapter.setNewData(invalid)
//                    isSelect = false
//                    setSelect(false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun findViewById() {
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}
