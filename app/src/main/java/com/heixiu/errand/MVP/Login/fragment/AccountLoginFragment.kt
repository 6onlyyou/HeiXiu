package com.heixiu.errand.MVP.Login.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountLoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountLoginFragment : BaseFragment() {
    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_account_login, container, false)
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun initView() {
    }
}// Required empty public constructor