package com.example.aehar.bookapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by aehar on 2/28/2019.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabFragment2();
            case 1:
                return new TabFragment3();
            case 2:
                return new TabFragment4();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
