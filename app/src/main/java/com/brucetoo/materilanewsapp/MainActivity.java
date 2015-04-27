package com.brucetoo.materilanewsapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.brucetoo.materilanewsapp.activity.BaseActivity;
import com.brucetoo.materilanewsapp.fragment.TopNewsFragment;
import com.brucetoo.materilanewsapp.utils.DensityUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.viewpager_tab)
    SmartTabLayout mTabLayout;
    @InjectView(R.id.viewpager)
    ViewPager mViewPager;

    private boolean mToolBarAnimation;//toolBar 第一次进入界面时动画
    private FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) { // 该界面第一次进入的时候中心toolbar动画
            mToolBarAnimation = true;
        }
        setToolBar("Hot Top News");
        initView();
    }

    /**
     * 初始化viewpager和strip
     */
    private void initView() {
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.topnews, TopNewsFragment.class)
                .add(R.string.topnews, TopNewsFragment.class)
                .add(R.string.topnews, TopNewsFragment.class)
                .add(R.string.topnews, TopNewsFragment.class)
                .create());

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setViewPager(mViewPager);
        //滑动viewpager改变title
        mTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            //    setToolBarTitle("Top:"+position);
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

}
