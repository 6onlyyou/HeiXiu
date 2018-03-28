package com.example.app.MVP.Home


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
class HomeFragment : BaseFragment() {


    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}
