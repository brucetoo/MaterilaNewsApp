package com.brucetoo.materilanewsapp.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by Bruce Too
 * On 4/22/15.
 * At 14:33
 */
public class HttpUtil {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url,ResponseHandlerInterface handler){
        client.get(url, handler);
    }

    public static void post(String url,ResponseHandlerInterface handler) {
        client.post(url, handler);
    }
}
