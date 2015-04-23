package com.brucetoo.materilanewsapp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruce Too
 * On 4/22/15.
 * At 14:38
 */
public class JsonUtil {

    //jsonToBean
    public static <T> T json2Bean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public static String createJsonString(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }


    // 使用Gson进行解析 List<T>
    public static <T> List<T> json2Beans(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }
}
