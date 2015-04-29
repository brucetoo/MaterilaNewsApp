package com.brucetoo.materilanewsapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brucetoo.materilanewsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by Bruce Too
 * On 4/29/15.
 * At 11:01
 */
public class ImageDetailActivity extends SwipeBackActivity{

    @InjectView(R.id.vp_img_pager)
    ViewPager mImgPaper;
    @InjectView(R.id.tv_img_count)
    TextView mImgCount;

    private List<String> mImgs;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        setToolBar("图片详情",R.drawable.btn_back);
        mImgs = getIntent().getStringArrayListExtra("imgs");
        mAdapter = new ImageAdapter();
        mImgPaper.setAdapter(mAdapter);
        mImgCount.setText("1/"+mImgs.size());
        mImgPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mImgCount.setText(position+1+"/"+mImgs.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ImageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = new ImageView(ImageDetailActivity.this);
            img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Picasso.with(ImageDetailActivity.this).load(mImgs.get(position)).placeholder(R.drawable.img_loading).into(img);
            ((ViewPager)container).addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((ImageView) object);
        }
    }
}
