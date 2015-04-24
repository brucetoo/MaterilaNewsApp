package com.brucetoo.materilanewsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bruce Too
 * On 4/22/15.
 * At 14:41
 */
public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public final static int HANDLE_DATA_FAUILED = 1000;
    public final static int HANDLE_DATA_SUCCESS = 1001;
    public final static int HANDLE_TOPNEWS_REFRESH = 1002;


    protected View mRootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater);
        return mRootView;
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
}
