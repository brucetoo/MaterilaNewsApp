package com.brucetoo.materilanewsapp.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.adapter.TopNewsRecyclerAdapter;
import com.brucetoo.materilanewsapp.constant.AddressConstant;
import com.brucetoo.materilanewsapp.model.NewsModel;
import com.brucetoo.materilanewsapp.utils.HttpUtil;
import com.brucetoo.materilanewsapp.utils.JsonUtil;
import com.brucetoo.materilanewsapp.utils.NetWorkUtil;
import com.brucetoo.materilanewsapp.widget.superrecyclerview.SuperRecyclerView;
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
    SuperRecyclerView mRecyclerView;

    private TopNewsRecyclerAdapter adapter;
    private RelativeLayout mRootView;

    protected Handler mViewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_DATA_SUCCESS:
                    NewsModel model = (NewsModel) msg.obj;
                    if (adapter == null) {
                        adapter = new TopNewsRecyclerAdapter(getActivity());
                        adapter.setNewsIds(model.T1348647909107);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mRecyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }

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
        HttpUtil.get(AddressConstant.TopUrl + AddressConstant.TopId + "/0" + AddressConstant.END_URL,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        mViewHandler.sendEmptyMessage(HANDLE_DATA_FAUILED);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        NewsModel newsModel = JsonUtil.json2Bean(response.toString(), NewsModel.class);
                        mViewHandler.obtainMessage(HANDLE_DATA_SUCCESS, newsModel).sendToTarget();
                    }
                });
    }

}
