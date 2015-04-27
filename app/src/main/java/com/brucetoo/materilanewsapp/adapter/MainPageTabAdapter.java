package com.brucetoo.materilanewsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brucetoo.materilanewsapp.fragment.TopNewsFragment;


/**
 * Created by Bruce Too
 * On 4/27/15.
 * At 14:59
 */
public class MainPageTabAdapter extends FragmentStatePagerAdapter{

    private static String TITLES[] = new String[]{"头条","头条","头条","头条","头条","头条","头条","头条"};

    public MainPageTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f;
//        switch (position){
//            case 0:
//
//                break;
//        }
        f = TopNewsFragment.newInstance(null);
        return f;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

}
