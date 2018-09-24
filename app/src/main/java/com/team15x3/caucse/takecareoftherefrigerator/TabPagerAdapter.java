package com.team15x3.caucse.takecareoftherefrigerator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    public TabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                TabHomeFragment tabHomeFragment = new TabHomeFragment();
                return tabHomeFragment;
            case 1:
                TabRecipeFragment tabRecipeFragment = new TabRecipeFragment();
                return tabRecipeFragment;
            case 2:
                TabSettingFragment tabSettingFragment = new TabSettingFragment();
                return tabSettingFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
