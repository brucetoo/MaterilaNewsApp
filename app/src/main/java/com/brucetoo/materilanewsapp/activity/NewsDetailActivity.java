package com.brucetoo.materilanewsapp.activity;

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
import com.brucetoo.materilanewsapp.widget.EmptyViewLayout;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.InjectView;

/**
 * Created by Bruce Too
 * On 4/24/15.
 * At 15:45
 */
public class NewsDetailActivity extends BaseActivity {
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

    private EmptyViewLayout emptyViewLayout;

    private String mUrl;
    private String mDocId;
    private NewsDetailModel newsDetailModel;
    private static int HANDLE_REFESH_UI = 1001;
    private  Handler detaiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
             if(msg.what == HANDLE_REFESH_UI){
                 mHtmlText.setHtmlFromString(newsDetailModel.body,false);
                 mTitle.setText(newsDetailModel.title);
                 mSource.setText(newsDetailModel.source);
                 mTime.setText(newsDetailModel.time);
                 if(newsDetailModel.img.size() > 0) {
                     Picasso.with(NewsDetailActivity.this)
                             .load(newsDetailModel.img.get(0))
                             .placeholder(R.drawable.img_loading)
                             .error(R.drawable.img_loading_fail)
                             .into(mFirstImg);
                     mTotalImg.setText("共"+newsDetailModel.img.size()+"张图");
                 }else {
                     mFirstImg.setVisibility(View.GONE);
                     mTotalImg.setVisibility(View.GONE);
                 }
             }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setToolBar("新闻详情");

        mUrl = getIntent().getStringExtra("url");
        mDocId = getIntent().getStringExtra("docid");
        Logger.d(mUrl);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
