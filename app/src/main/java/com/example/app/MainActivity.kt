package com.example.app

import android.app.Fragment
import android.view.View
import android.widget.ImageView
import com.example.app.MVP.Community.CommunityFragment
import com.example.app.MVP.Contentt.ContentFragment
import com.example.app.MVP.Express.ExpressFragment
import com.example.app.MVP.Home.HomeFragment
import com.example.app.MVP.Message.MessageFragment
import com.example.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private var fragments: ArrayList<Fragment>? = null
    override fun findViewById() {

        Rl_homepass.setOnClickListener(View.OnClickListener {
            switchFragment(0)
        }) ;
        Rl_expressnopass.setOnClickListener(View.OnClickListener {
            switchFragment(1)
        }) ;
        Rl_contentt.setOnClickListener(View.OnClickListener {
            switchFragment(2)
        }) ;
        Rl_communitynopass.setOnClickListener(View.OnClickListener {
            switchFragment(3)
        }) ;
        Rl_messnopass.setOnClickListener(View.OnClickListener {
            switchFragment(4)
        }) ;
    }



    override fun loadViewLayout() {
        setContentView(R.layout.activity_main)
        fragments = ArrayList()
        addFragment()
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

    /**
     * 创建fragment实例并把他们加入集合
     */
    fun addFragment() {
        fragments!!.add(HomeFragment())
        fragments!!.add(ExpressFragment())
        fragments!!.add(ContentFragment())
        fragments!!.add(CommunityFragment())
        fragments!!.add(MessageFragment())
    }

    /**
     * 点击切换fragment
     *
     * @param position
     */
    fun switchFragment(position: Int) {
        val fragmentTransaction = getFragmentManager().beginTransaction()
        for (i in 0 until fragments!!.size) {
            val fragment = fragments!!.get(i)
            if (i == position) {
                if (fragment.isAdded) {
                    fragmentTransaction.show(fragment)
                } else {
                    fragmentTransaction.add(R.id.fl_container, fragment)
                }
            } else {
                if (fragment.isAdded) {
                    fragmentTransaction.hide(fragment)
                }
            }
        }
        //提交事务
        fragmentTransaction.commit()
    }
}
