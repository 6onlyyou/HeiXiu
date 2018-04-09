package com.example.app.MVP.Contentt


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
class ContentFragment : BaseFragment() {
    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_content, container, false)
    }

    override fun initListener() {
    }

    override fun initData() {
    }




}// Required empty public constructor
