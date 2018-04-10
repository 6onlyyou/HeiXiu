package com.example.app.MVP.Home


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.R
import com.example.app.adapter.HomeAdapter
import com.example.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    private val homeAdapter by lazy {
        HomeAdapter(context)
    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }


    override fun initView() {
        homeRv.layoutManager = LinearLayoutManager(context)
        homeAdapter.onItemClick = object : HomeAdapter.OnItemClick {
            override fun onDetailClick(position: Int) {
                startActivity(Intent(context, OrderDetailActivity::class.java))
            }

            override fun onConfirmOrderClick(position: Int) {
                startActivity(Intent(context, ConfirmOrderActivity::class.java))
            }
        }
        homeRv.adapter = homeAdapter
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}
