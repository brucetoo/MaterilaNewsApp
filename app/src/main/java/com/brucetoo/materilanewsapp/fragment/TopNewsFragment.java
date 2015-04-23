package com.brucetoo.materilanewsapp.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.constant.AddressConstant;
import com.brucetoo.materilanewsapp.model.NewsModel;
import com.brucetoo.materilanewsapp.utils.HttpUtil;
import com.brucetoo.materilanewsapp.utils.JsonUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Bruce Too
 * On 4/21/15.
 * At 18:32
 */
public class TopNewsFragment extends BaseFragment {

    @InjectView(R.id.rv_top_news)
    RecyclerView mRecyclerView;

    protected Handler mViewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_DATA_SUCCESS:

                    break;
                case HANDLE_DATA_FAUILED:

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
        View view = inflater.inflate(R.layout.frament_top_news, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        HttpUtil.get(AddressConstant.TopUrl + AddressConstant.TopId + "/0" + AddressConstant.END_URL,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        mRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        NewsModel newsModel = JsonUtil.json2Bean(response.toString(), NewsModel.class);
                        mLoadingProgress.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mViewHandler.obtainMessage(HANDLE_DATA_SUCCESS,newsModel).sendToTarget();
                    }
                });
    }

    @Override
    public void onRefresh() {
        //HANDLE_TOPNEWS_REFRESH
        mRefreshLayout.setRefreshing(false);
    }
}
