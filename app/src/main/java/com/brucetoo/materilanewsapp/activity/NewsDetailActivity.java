package com.brucetoo.materilanewsapp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.widget.EmptyViewLayout;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.InjectView;

/**
 * Created by Bruce Too
 * On 4/24/15.
 * At 15:45
 */
public class NewsDetailActivity extends BaseActivity {

    @InjectView(R.id.wb_news_detail)
    WebView mWebView;

    private EmptyViewLayout emptyViewLayout;
    private final static String CACHE_NAME = "bruce";
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setToolBar("新闻详情");

        mUrl = getIntent().getStringExtra("url");
        Logger.d(mUrl);

        emptyViewLayout = new EmptyViewLayout(this,mWebView);
        initWebView();
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(mUrl);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                emptyViewLayout.showLoading(); //设置显示加载中布局
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                emptyViewLayout.showContentView();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
               emptyViewLayout.showError();
            }
        });
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);	//设置缓存模式
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()+CACHE_NAME;
//		String cacheDirPath = getCacheDir().getAbsolutePath()+CACHE_NAME;
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+CACHE_NAME);
        Logger.d("appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Logger.d( "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Logger.d("delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Logger.d( "delete file no exists " + file.getAbsolutePath());
        }
    }
}
