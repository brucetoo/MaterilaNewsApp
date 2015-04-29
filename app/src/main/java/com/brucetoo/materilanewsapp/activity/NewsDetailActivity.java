package com.brucetoo.materilanewsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.model.NewsDetailModel;
import com.brucetoo.materilanewsapp.utils.HttpUtil;
import com.brucetoo.materilanewsapp.utils.JsonUtil;
import com.brucetoo.materilanewsapp.utils.StringUtil;
import com.brucetoo.materilanewsapp.widget.EmptyViewLayout;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.InjectView;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Bruce Too
 * On 4/24/15.
 * At 15:45
 */
public class NewsDetailActivity extends SwipeBackActivity {
    @InjectView(R.id.html_text)
    HtmlTextView mHtmlText;
    @InjectView(R.id.tv_title)
    TextView mTitle;
    @InjectView(R.id.tv_source)
    TextView mSource;
    @InjectView(R.id.tv_time)
    TextView mTime;
    @InjectView(R.id.iv_first_img)
    ImageView mFirstImg;
    @InjectView(R.id.tv_total_img)
    TextView mTotalImg;
    @InjectView(R.id.ll_holder)
    View mHolder;
    @InjectView(R.id.fl_img_holder)
    View mImgHolder;
    @InjectView(R.id.fl_video_holder)
    View mVideoHolder;
    @InjectView(R.id.iv_video_img)
    ImageView mVideoImg;

    private EmptyViewLayout emptyViewLayout;
    private SwipeBackLayout mSwipeBackLayout;

    private String mUrl;
    private String mDocId;
    private NewsDetailModel newsDetailModel;
    private static int HANDLE_REFESH_UI = 1001;
    private Handler detaiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLE_REFESH_UI) {
                mHtmlText.setHtmlFromString(newsDetailModel.body, false);
                mTitle.setText(newsDetailModel.title);
                mSource.setText(newsDetailModel.source);
                mTime.setText(newsDetailModel.time);
                //还处理视频存在的时候
                if (newsDetailModel.img.size() != 0) { //图片集
                    mVideoHolder.setVisibility(View.GONE);
                    mImgHolder.setVisibility(View.VISIBLE);
                        Picasso.with(NewsDetailActivity.this)
                                .load(newsDetailModel.img.get(0))
                                .placeholder(R.drawable.img_loading)
                                .error(R.drawable.img_loading_fail)
                                .into(mFirstImg);
                        mTotalImg.setText("共" + newsDetailModel.img.size() + "张图");
                } else if (StringUtil.isNotNullOrEmpty(newsDetailModel.url_mp4)) {
                    mVideoHolder.setVisibility(View.VISIBLE);
                    mImgHolder.setVisibility(View.GONE);
                    Picasso.with(NewsDetailActivity.this)
                            .load(newsDetailModel.cover)
                            .placeholder(R.drawable.img_loading)
                            .error(R.drawable.img_loading_fail)
                            .into(mVideoImg);
                }else {
                    mHolder.setVisibility(View.GONE);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setToolBar("新闻详情", R.drawable.btn_back);

        mUrl = getIntent().getStringExtra("url");
        mDocId = getIntent().getStringExtra("docid");
        Logger.d(mUrl);

        //左滑关闭activity
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        emptyViewLayout = new EmptyViewLayout(this, mHtmlText);
        emptyViewLayout.showLoading();

        HttpUtil.get(mUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                emptyViewLayout.showError();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                emptyViewLayout.showContentView();
                try {
                    JSONObject jsonObject = new JSONObject(responseString).getJSONObject(mDocId);
                    newsDetailModel = JsonUtil.parseNewsDetail(jsonObject);
                    detaiHandler.sendEmptyMessage(HANDLE_REFESH_UI);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @OnClick(R.id.iv_first_img)
    public void onClickImg(View view) {
        Intent intent = new Intent(NewsDetailActivity.this, ImageDetailActivity.class);
        intent.putStringArrayListExtra("imgs", (java.util.ArrayList<String>) newsDetailModel.img);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
