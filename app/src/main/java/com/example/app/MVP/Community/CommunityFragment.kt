package com.example.app.MVP.Community


import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.R
import com.example.app.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class CommunityFragment : BaseFragment() {


    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_community, container, false)
    }
    override fun initView() {

    }
    override fun initListener() {
    }

    override fun initData() {
    }


}// Required empty public constructor
