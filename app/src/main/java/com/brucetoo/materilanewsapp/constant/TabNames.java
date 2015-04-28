package com.brucetoo.materilanewsapp.constant;

import android.app.Activity;

import com.brucetoo.materilanewsapp.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bruce Too
 * On 4/27/15.
 * At 16:48
 */
public class TabNames {

    public static ArrayList<String> tabNames = new ArrayList<String>();

    public static ArrayList<String> getTabNames(Activity activity) {
        tabNames.clear();
        String[] stringArray = activity.getResources().getStringArray(R.array.tab_name);
        tabNames.addAll(Arrays.asList(stringArray));
        return tabNames;
    }

    public void setTabNames(ArrayList<String> tabNames) {
        this.tabNames = tabNames;
    }

}
