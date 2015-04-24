package com.brucetoo.materilanewsapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brucetoo.materilanewsapp.model.NewsModel;
import com.brucetoo.materilanewsapp.utils.HttpUtil;
import com.brucetoo.materilanewsapp.utils.JsonUtil;
import com.brucetoo.materilanewsapp.utils.SharePrefUtil;
import com.brucetoo.materilanewsapp.utils.StringUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Bruce Too
 * On 4/22/15.
 * At 14:41
 */
public abstract class BaseFragment extends Fragment{
    public final static int HANDLE_DATA_FAIILED = 1000;
    public final static int HANDLE_DATA_SUCCESS = 1001;
    public final static int HANDLE_TOPNEWS_REFRESH = 1002;
    public final static int HANDLE_TOPNEWS_REFRESH_FAIILED = 1002;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化view
     * @param inflater
     * @return
     */
    protected abstract View initView(LayoutInflater inflater);


    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 联网获取数据(缓存读取为先)
     * @param url
     * @param handler
     * @param isRefresh 是否更新缓存数据
     */
    protected void loadData(String url,final Handler handler,boolean isRefresh){
        if(!StringUtil.isEmpty(SharePrefUtil.getTopNews(getActivity())) && !isRefresh){
            NewsModel newsModel = JsonUtil.json2Bean(SharePrefUtil.getTopNews(getActivity()), NewsModel.class);
            handler.obtainMessage(HANDLE_DATA_SUCCESS, newsModel).sendToTarget();
        }else {
            HttpUtil.get(url,
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            handler.sendEmptyMessage(HANDLE_DATA_FAIILED);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            SharePrefUtil.saveTopNews(getActivity(), response.toString());
                            NewsModel newsModel = JsonUtil.json2Bean(response.toString(), NewsModel.class);
                            handler.obtainMessage(HANDLE_DATA_SUCCESS, newsModel).sendToTarget();
                            Toast.makeText(getActivity(), "刷新成功...", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    /**
     * 加载更多
     * @param url
     * @param handler
     */
    protected void loadMoreData(String url,final Handler handler){
        HttpUtil.get(url,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        handler.sendEmptyMessage(HANDLE_DATA_FAIILED);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        NewsModel newsModel = JsonUtil.json2Bean(response.toString(), NewsModel.class);
                        handler.obtainMessage(HANDLE_TOPNEWS_REFRESH, newsModel).sendToTarget();
                    }
                });
    }
}
