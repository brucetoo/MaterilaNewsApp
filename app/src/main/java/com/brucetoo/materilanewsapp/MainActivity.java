package com.brucetoo.materilanewsapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.brucetoo.materilanewsapp.activity.BaseActivity;
import com.brucetoo.materilanewsapp.adapter.MainPageTabAdapter;
import com.brucetoo.materilanewsapp.utils.DensityUtil;
import com.brucetoo.materilanewsapp.view.SortItemPopWin;
import com.brucetoo.materilanewsapp.widget.DragGrid;
import com.brucetoo.materilanewsapp.widget.SlidingTabLayout;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.sliding_tabs)
    SlidingTabLayout mTabLayout;
    @InjectView(R.id.viewpager)
    ViewPager mViewPager;

    private boolean mToolBarAnimation;//toolBar 第一次进入界面时动画
    private MainPageTabAdapter adapter;
    private SortItemPopWin mPopWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) { // 该界面第一次进入的时候中心toolbar动画
            mToolBarAnimation = true;
        }
        setToolBar("头条");
        initView();
    }

    /**
     * 初始化viewpager和table_layout
     */
    private void initView() {
        adapter = new MainPageTabAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(adapter);
        mTabLayout.setDistributeEvenly(true);//填充满屏幕宽度
        mTabLayout.setCustomTabView(R.layout.view_tab_item, R.id.text); //该方法必须在setViewPager前调用
        mTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.tab_text_color_select));
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 setToolBarTitle(adapter.getPageTitle(position).toString()  );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 重写父方法的构建 menu 的方法，
     * 执行toolbar动画
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (mToolBarAnimation) {
            mToolBarAnimation = false;
            mTabLayout.setAlpha(0);
            mTabLayout.setTranslationY(-DensityUtil.dip2px(48));
            startToolBarAnimation(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //动画执行完毕执行主界面的动画
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(mTabLayout, "alpha", 1),
                            ObjectAnimator.ofFloat(mTabLayout, "translationY", 0)
                    );
                    set.setDuration(500).start();
                }
            });
        }
        return true;
    }

    @OnClick(R.id.iv_sort)
    public void onSortClick(View view){
        mPopWin = new SortItemPopWin(MainActivity.this);
        mPopWin.setSortGrid(new DragGrid.OnClickItem() { //item点击事件
            @Override
            public void onItemClicked(final int position) {
                mPopWin.dismiss();
                //防止不立即滚动到指定位置
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mViewPager.setCurrentItem(position, true);
                    }
                });

            }
        });
        mPopWin.show(view);
        mPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {// popwindow消失的监听事件
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                MainActivity.this.getWindow().setAttributes(lp);
                initView();
            }
        });
    }
}
