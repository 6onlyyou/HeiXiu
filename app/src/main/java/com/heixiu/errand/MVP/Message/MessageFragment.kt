package com.heixiu.errand.MVP.Message


import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : BaseFragment() {
    override fun initView() {

    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_message, container, false)

    }

    override fun initListener() {
    }

    override fun initData() {
    }



}// Required empty public constructor
