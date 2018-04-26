package com.example.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description:
 * Data：2018/4/24-15:07
 * Author: fushuaige
 */
public class LoginFragmentAdapter extends FragmentPagerAdapter {

    private String [] title = {"手机快捷登入","账号密码登入"};
    private List<Fragment> fragmentList;
    public LoginFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
