package com.brucetoo.materilanewsapp.application;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by Bruce Too
 * On 4/22/15.
 * At 10:22
 */
public class NewsApplication extends Application{

    private final static String TAG = "NEWSAPP";

    @Override
    public void onCreate() {
        super.onCreate();

        initLog();
    }

    /**
     * Log 全局配置
     */
    private void initLog() {
          Logger.init(TAG)               // default tag
                .setMethodCount(2);            // default 2     log所在方法的
        // .hideThreadInfo()             // default shown       显示log线程
        // .setLogLevel(LogLevel.NONE);  // default LogLevel.FULL  //屏蔽log
    }
}
