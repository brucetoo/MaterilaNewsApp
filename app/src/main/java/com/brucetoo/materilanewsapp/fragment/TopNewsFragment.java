package com.brucetoo.materilanewsapp.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.adapter.TopNewsRecyclerAdapter;
import com.brucetoo.materilanewsapp.constant.AddressConstant;
import com.brucetoo.materilanewsapp.model.NewsModel;
import com.brucetoo.materilanewsapp.utils.NetWorkUtil;
import com.brucetoo.materilanewsapp.widget.superrecyclerview.OnMoreListener;
import com.brucetoo.materilanewsapp.widget.superrecyclerview.SuperRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Bruce Too
 * On 4/21/15.
 * At 18:32
 * 头条新闻页
 */
public class TopNewsFragment extends BaseFragment {

    @InjectView(R.id.rv_top_news)
    SuperRecyclerView mRecyclerView;

    private TopNewsRecyclerAdapter adapter;
    private RelativeLayout mRootView;
    private static String TOP_NEWS_URL = AddressConstant.TopUrl + AddressConstant.TopId + "/pageIndex" + AddressConstant.END_URL;
    private static int PAGE_LEFT = 1;//当前显示的item滑动到底还剩1个的时候，开始加载下一页
    private int currentPage = 0;

    protected Handler mViewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            NewsModel model;
            switch (msg.what) {
                case HANDLE_DATA_SUCCESS:
                    model = (NewsModel) msg.obj;
                    adapter = new TopNewsRecyclerAdapter(getActivity());
                    adapter.setNewsIds(model.T1348647909107);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(adapter);
                    Toast.makeText(getActivity(), "刷新成功...", Toast.LENGTH_SHORT).show();
                    break;
                case HANDLE_DATA_FAIILED:
                    Toast.makeText(getActivity(), "获取失败...", Toast.LENGTH_SHORT).show();
                    break;
                case HANDLE_TOPNEWS_REFRESH:
                    model = (NewsModel) msg.obj;
                    adapter.addNews(model.T1348647909107);
                    mRecyclerView.hideMoreProgress();
                    break;
            }
        }
    };
//    public static TopNewsFragment newInstance(Bundle bundle){
//        TopNewsFragment fragment = new TopNewsFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    protected View initView(LayoutInflater inflater) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.frament_top_news, null);
        ButterKnife.inject(this, mRootView);
        return mRootView;
    }

    @Override
    protected void initData() {
        super.initData();
        if (!NetWorkUtil.isNetworkAvailable(getActivity())) {
            mRootView.removeAllViews();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_errorview, null);
            mRootView.addView(view);
        }
        mRecyclerView.setRefreshingColor(getResources().getColor(R.color.style_color_primary));
        mRecyclerView.setRefreshListener(refreshListener);
        mRecyclerView.setupMoreListener(moreListener,PAGE_LEFT);
        loadData(TOP_NEWS_URL.replace("pageIndex", 0 + ""), mViewHandler);
    }

    /**
     * 下拉刷新
     */
    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData(TOP_NEWS_URL.replace("pageIndex",0 + ""), mViewHandler);
        }
    };

    /**
     * 加载更多
     */
    OnMoreListener moreListener = new OnMoreListener() {
        @Override
        public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
            currentPage += 1;
            loadMoreData(TOP_NEWS_URL.replace("pageIndex", currentPage * 20 + ""), mViewHandler);
        }
    };
}
