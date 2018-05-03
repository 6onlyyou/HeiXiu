package com.heixiu.errand.MVP.Login.fragment

import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment

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
