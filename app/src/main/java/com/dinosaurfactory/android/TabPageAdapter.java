package com.dinosaurfactory.android;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPageAdapter extends FragmentPagerAdapter {

    private int tabCount;

    public TabPageAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ItemListFragment();
                break;
            case 2:
                fragment = new ReviewFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount(){
        return tabCount;
    }
}
