package com.example.app.MVP.Message


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.app.R
import com.example.app.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : BaseFragment() {
    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_message, container, false)
    }

    override fun initListener() {
    }

    override fun initData() {
    }



}// Required empty public constructor
