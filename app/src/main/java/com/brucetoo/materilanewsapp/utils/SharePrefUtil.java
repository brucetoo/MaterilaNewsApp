package com.brucetoo.materilanewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bruce Too
 * On 4/23/15.
 * At 10:29
 */
public class SharePrefUtil {

    public final static String TOP_NEWS = "topnews";//topnews数据sharePref常量

    /**
     * Mode用  MODE_MULTI_PROCESS 而不用 MODE_PRIVATE原因
     * MODE_MULTI_PROCESS这个值是一个标志，在Android 2.3及以前，
     * 这个标志位都是默认开启的，允许多个进程访问同一个SharedPrecferences对象。
     * 而以后的Android版本，必须通过明确的将MODE_MULTI_PROCESS这个值传递给mode参数，才能开启多进程访问
     * @param ctx
     * @param prefName
     * @return
     */
    public static SharedPreferences getSharedPref(Context ctx, String prefName) {
        SharedPreferences sp = ctx.getSharedPreferences(prefName, Context.MODE_MULTI_PROCESS);
        return sp;
    }

    /**
     * 保存topnews数据
     * @param context
     * @param topnews
     */
    public static void saveTopNews(Context context,String topnews){
        SharedPreferences sp = getSharedPref(context,TOP_NEWS);
        sp.edit().putString(TOP_NEWS,topnews).commit();
    }

    /**
     * 获取topnews数据
     * @param context
     * @return
     */
    public static String getTopNews(Context context){
        SharedPreferences sp = getSharedPref(context,TOP_NEWS);
        return sp.getString(TOP_NEWS,"");
    }
}
