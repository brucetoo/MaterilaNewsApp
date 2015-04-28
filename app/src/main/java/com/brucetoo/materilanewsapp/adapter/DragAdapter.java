
package com.brucetoo.materilanewsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.utils.SharePrefUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

public class DragAdapter extends BaseAdapter {
    /** TAG */
    private final static String TAG = "DragAdapter";
    private final Context context;
    /**拖动放下的postion */
    private int holdPosition;
    /**item位置是否变化*/
    private boolean isChanged = false;
    /** tab页数据 */
    public List<String> tabNameList;
    private boolean isItemShow = false;
    private TextView sort_item;


    public DragAdapter(Context context, List<String> tabNameList) {
        this.context = context;
        this.tabNameList = tabNameList;
    }

    @Override
    public int getCount() {
        return tabNameList == null ? 0 : tabNameList.size();
    }

    @Override
    public String getItem(int position) {
        if (tabNameList != null && tabNameList.size() != 0) {
            return tabNameList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_drag_sort, null);
        sort_item = (TextView) view.findViewById(R.id.sort_item);
        sort_item.setText(tabNameList.get(position));
        if ((position == 0)) {//第一个不能排序
            sort_item.setSelected(true);//选中
            sort_item.setEnabled(false);//不能点击
        }else {
            //拖拽变化后隐藏底部的item
            if (isChanged && (position == holdPosition) && !isItemShow) {
                sort_item.setText("");
                isChanged = false;
            }
            sort_item.setSelected(false);
            sort_item.setEnabled(false);
        }
//        if ((position == -1 + tabNameList.size())) {
//            sort_item.setText("");
//            sort_item.setSelected(true);
//            sort_item.setEnabled(true);
//        }
        return view;
    }

    /**
     * 拖动重新排序
     * @param dragPostion 拖的位置
     * @param dropPostion 放下的位置
     */
    public void exchange(int dragPostion, int dropPostion) {
        holdPosition = dropPostion;
        String dragItem = getItem(dragPostion);
        Logger.i(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
        if (dragPostion < dropPostion) {
            tabNameList.add(dropPostion + 1, dragItem);
            tabNameList.remove(dragPostion);
        } else {
            tabNameList.add(dropPostion, dragItem);
            tabNameList.remove(dragPostion + 1);
        }
        isChanged = true;
        SharePrefUtil.saveTabSortNames(context,tabNameList);
        notifyDataSetChanged();
    }

    /**
     * 隐藏放下的item
     * @param show
     */
    public void setShowDropItem(boolean show) {
        isItemShow = show;
    }

}
