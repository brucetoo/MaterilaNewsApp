package com.brucetoo.materilanewsapp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.brucetoo.materilanewsapp.MainActivity;
import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.adapter.DragAdapter;
import com.brucetoo.materilanewsapp.utils.SharePrefUtil;
import com.brucetoo.materilanewsapp.widget.DragGrid;

/**
 * Created by Bruce Too
 * On 4/27/15.
 * At 16:58
 */
public class SortItemPopWin extends PopupWindow {

    private Context mContext;
    private DragGrid mDragGrid;
    private DragAdapter mAdapter;
    private Activity mActivity;

    public SortItemPopWin(Context ctx) {
        mContext = ctx;
        mActivity = ((MainActivity)mContext);
        init();
    }

    /**
     * 初始化popwindow
     */
    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_sort_item, null);
        mDragGrid = (DragGrid) view.findViewById(R.id.dg_sort);
        //背景变暗
        WindowManager.LayoutParams layoutParams = ((MainActivity)mContext).getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        ((MainActivity)mContext).getWindow().setAttributes(layoutParams);
        setContentView(view);
        setContentView(view);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(false);
        setFocusable(true);
        setAnimationStyle(R.style.FadePopWin);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //dismiss时候背景恢复
//        setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
//                lp.alpha = 1f;
//                mActivity.getWindow().setAttributes(lp);
//            }
//        });
    }

    /**
     * 设置拖动排序grid
     */
    public void setSortGrid(DragGrid.OnClickItem onClickItem) {
        mAdapter = new DragAdapter(mContext, SharePrefUtil.getTabSortNames(mContext));
        mDragGrid.setAdapter(mAdapter);
        mDragGrid.setOnClickItem(onClickItem);
    }

    /**
     * 显示popwindow
     *
     * @param parentView
     */
    public void show(View parentView) {
        if (parentView != null) {
          //  showAtLocation(parentView, Gravity.CENTER, 0, 0);
            showAsDropDown(parentView);
        }
    }
}
