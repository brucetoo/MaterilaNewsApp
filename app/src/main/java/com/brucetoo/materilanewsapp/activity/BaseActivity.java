package com.brucetoo.materilanewsapp.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.brucetoo.materilanewsapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by Bruce Too
 * On 4/21/15.
 * At 15:51
 */
public class BaseActivity extends ActionBarActivity{
    // Optional except An exception will be thrown if the target view cannot be found
    @Optional
    @InjectView(R.id.tool_bar)
    Toolbar mToolBar;

    @Optional
    @InjectView(R.id.title)
    TextView mTitle;

    protected MenuItem inboxMenuItem;

    /**
     * 子 activiy继承改方法就可以使用注解
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    /**
     *  设置toolbar
     *  @param title top title
     */
    protected void setToolBar(String title) {
        if(mToolBar != null){
            setSupportActionBar(mToolBar);
            mToolBar.setNavigationIcon(R.drawable.ic_menu_white);
        }


    }

    /**
     * toolBar菜单设置
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //以下去除了 toolbar item点击时的背景高度和 toolbar的高度不匹配
        inboxMenuItem = menu.findItem(R.id.action_inbox);
        inboxMenuItem.setActionView(R.layout.menu_item_view);
        return true;
    }

    /**
     * toolbar item点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
