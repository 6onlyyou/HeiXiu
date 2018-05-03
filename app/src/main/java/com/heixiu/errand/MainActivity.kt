package com.heixiu.errand

import com.heixiu.errand.MVP.Community.CommunityFragment
import com.heixiu.errand.MVP.Contentt.ContentFragment
import com.heixiu.errand.MVP.Express.ExpressFragment
import com.heixiu.errand.MVP.Home.HomeFragment
import com.heixiu.errand.MVP.Message.MessageFragment
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private var fragments: ArrayList<android.support.v4.app.Fragment>? = null
    override fun findViewById() {

        Rl_homepass.setOnClickListener({
            switchFragment(0)
        });
        Rl_expressnopass.setOnClickListener({
            switchFragment(1)
        });
        Rl_contentt.setOnClickListener({
            switchFragment(2)
        });
        Rl_communitynopass.setOnClickListener({
            switchFragment(3)
        });
        Rl_messnopass.setOnClickListener({
            switchFragment(4)
        });
    }


    override fun loadViewLayout() {
        setContentView(R.layout.activity_main)
        fragments = ArrayList()
        addFragment()

        switchFragment(0)
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
        val fragmentTransaction = supportFragmentManager.beginTransaction()
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
