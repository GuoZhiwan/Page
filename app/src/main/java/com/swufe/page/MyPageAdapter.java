package com.swufe.page;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new FirstFragment();
        }else if(position==1){
            return new SecondFragment();
        }else{
            return new ThirdFragment();
        }

    }
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "首页";
        }else if(position==1){
            return "刷词";
        }else{
            return "搜词";
        }

    }
    @Override
    public int getCount() {
        return 3;
    }
}
