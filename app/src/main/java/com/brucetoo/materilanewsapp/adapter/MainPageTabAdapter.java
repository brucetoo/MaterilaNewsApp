package com.brucetoo.materilanewsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brucetoo.materilanewsapp.MainActivity;
import com.brucetoo.materilanewsapp.fragment.TopNewsFragment;
import com.brucetoo.materilanewsapp.utils.SharePrefUtil;

import java.util.List;


/**
 * Created by Bruce Too
 * On 4/27/15.
 * At 14:59
 */
public class MainPageTabAdapter extends FragmentStatePagerAdapter{

    private List<String> tabNames;

    public MainPageTabAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);
        tabNames = SharePrefUtil.getTabSortNames(mainActivity);
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
        return tabNames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }

}
