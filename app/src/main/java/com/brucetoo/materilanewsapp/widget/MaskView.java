package com.brucetoo.materilanewsapp.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;

import com.brucetoo.materilanewsapp.R;

public class MaskView extends View {

    private final long ANIMATE_TIME = 800;//动画执行时间
    private Paint mPiant = new Paint();
    private float[] mLocation = new float[2];
    private int mCurrentRadius;
    private int mTargetId = -1;
    private Bitmap targetBitmap;
    private onEnterEndListener onEnterEndListener;
    private AccelerateInterpolator interpolator = new AccelerateInterpolator();

    public MaskView.onEnterEndListener getOnEnterEndListener() {
        return onEnterEndListener;
    }

    public void setOnEnterEndListener(MaskView.onEnterEndListener onEnterEndListener) {
        this.onEnterEndListener = onEnterEndListener;
    }

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaskView, 0, 0);
        try {
            mTargetId = a.getResourceId(R.styleable.MaskView_target, 0);
        } catch (Exception e) {
        } finally {
            a.recycle();
        }
    }

    public void setMCurrentRadius(int mCurrentRadius) {
        this.mCurrentRadius = mCurrentRadius;
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                createShader();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void createShader() {
        //获取目标视图,draw a bitmap
        View target = getRootView().findViewById(mTargetId);
        targetBitmap = Bitmap.createBitmap(target.getWidth(), target.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(targetBitmap);
        target.draw(c);

        //用背景图来渲染
        Shader shader = new BitmapShader(targetBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPiant.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPiant.setAntiAlias(true);
        mPiant.setColor(Color.WHITE);
        if (mLocation != null)
            canvas.drawCircle(mLocation[0], mLocation[1], mCurrentRadius, mPiant);
    }

    /**
     * 进入时的动画
     * @param location
     */
    public void startEnterAnimate(float[] location){
        mLocation = location;
        animateEnterRadius();
    }

    /**
     * 退出界面时的动画
     * @param animatorListenerAdapter
     */
    public void startExitAnimate(AnimatorListenerAdapter animatorListenerAdapter){
        animateExitRadius(animatorListenerAdapter);
    }

    private void animateExitRadius(AnimatorListenerAdapter animatorListenerAdapter) {
        ObjectAnimator radiusDown = ObjectAnimator.ofInt(this, "mCurrentRadius",Math.max(getWidth(), getHeight()),0);
        radiusDown.setDuration(ANIMATE_TIME);
        radiusDown.setInterpolator(interpolator);
        radiusDown.start();
        radiusDown.addListener(animatorListenerAdapter);
    }

    private void animateEnterRadius() {

        ObjectAnimator radiusUp = ObjectAnimator.ofInt(this, "mCurrentRadius", 0, Math.max(getWidth(),getHeight()));
        radiusUp.setDuration(ANIMATE_TIME);
        radiusUp.setInterpolator(interpolator);
        radiusUp.start();
        radiusUp.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(onEnterEndListener != null){
                    onEnterEndListener.onEnterEnd(mTargetId);
                }
            }
        });
    }

    /**
     * 进入动画执行完后的回调
     */
    public interface onEnterEndListener{
       void onEnterEnd(int targetId);
    }
}
