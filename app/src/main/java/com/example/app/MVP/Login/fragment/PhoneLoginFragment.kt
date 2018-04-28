package com.example.app.MVP.Login.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.MainActivity

import com.example.app.R
import com.example.app.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PhoneLoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PhoneLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneLoginFragment : BaseFragment() {
    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_phone_login, container, false)
    }

    override fun initListener() {

    }

    override fun initData() {
    }

    override fun initView() {
    }

}// Required empty public constructor
