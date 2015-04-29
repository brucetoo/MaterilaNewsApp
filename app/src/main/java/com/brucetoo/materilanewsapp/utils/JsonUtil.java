package com.brucetoo.materilanewsapp.utils;

import com.brucetoo.materilanewsapp.model.NewsDetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruce Too
 * On 4/22/15.
 * At 14:38
 */
public class JsonUtil {

    //jsonToBean  解析能用gson解析的json数据
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

    public static String createJsonString(Object object) {
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


    /**
     * 解析新闻详情页
     * @param jsonObject
     */
    public static NewsDetailModel parseNewsDetail(JSONObject jsonObject) {
        NewsDetailModel model = new NewsDetailModel();
        try {
            model.body = jsonObject.getString("body");
            model.docid = jsonObject.getString("docid");
            model.source = jsonObject.getString("source");
            model.title = jsonObject.getString("title");
            model.time = jsonObject.getString("ptime");

            JSONArray jsonArray = jsonObject.getJSONArray("img");
            model.img = parseImgList(jsonArray);

            JSONArray video = jsonObject.getJSONArray("video");

            model.cover = video.getJSONObject(0).getString("cover");
            model.alt = video.getJSONObject(0).getString("cover");
            model.url_mp4 = video.getJSONObject(0).getString("url_mp4");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 解析图片集
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public static List<String> parseImgList(JSONArray jsonArray){
        List<String> imgList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
             imgList.add(jsonArray.getJSONObject(i).getString("src"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //imgList.add(jsonArray.getString(i));
        }
        return imgList;
    }
}
