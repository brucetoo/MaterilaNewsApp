package com.brucetoo.materilanewsapp.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.utils.DensityUtil;
import com.brucetoo.materilanewsapp.utils.StringUtil;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

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

    protected MenuItem mLikeItem; //toolbar menu
    private static final int TOOLBAR_ANIMATE_TIME = 500;  //toolbar 动画时间
    private Interpolator decelerateInterpolator = new DecelerateInterpolator();//加速

    /**
     * 子activiy继承改方法就可以使用注解
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        //setSystemBarTint();
    }

    /**
     *  设置toolbar
     *  @param title top title
     */
    protected void setToolBar(String title) {
        if(mToolBar != null){
            setSupportActionBar(mToolBar);
         //   ViewCompat.setElevation(mToolBar,getResources().getDimension(R.dimen.toolbar_elevation));
            mToolBar.setNavigationIcon(R.drawable.ic_menu_white);
            if(!StringUtil.isEmpty(title)) {
                mTitle.setText(title);
            }
        }
    }

    /**
     *  设置顶部title
     * @param title
     */
    protected void setToolBarTitle(String title) {
        if (!StringUtil.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

//    /**
//     * 设置系统导航栏和状态栏颜色
//     */
//    protected void setSystemBarTint(){
//        // create our manager instance after the content view is set
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        // enable status bar tint
//        tintManager.setStatusBarTintEnabled(true);
//        // enable navigation bar tint
//        //tintManager.setNavigationBarTintEnabled(true);
//        tintManager.setStatusBarTintColor(getResources().getColor(R.color.style_color_primary));
//
//    }

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
        mLikeItem = menu.findItem(R.id.action_like);
        mLikeItem.setActionView(R.layout.menu_item_view);
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

    /**
     * 开始执行toolBar进入动画
     */
    protected void startToolBarAnimation(AnimatorListenerAdapter onAnimationEnd) {
        int actionBarSize = DensityUtil.dip2px(56);
        //toolbar整体上移
        mToolBar.setTranslationY(-actionBarSize);
        mTitle.setTranslationY(-actionBarSize);
        //MenuItem要先获取到到actionView才能向上移动
        mLikeItem.getActionView().setTranslationY(-actionBarSize);

        animate(mToolBar).translationY(0).setDuration(TOOLBAR_ANIMATE_TIME).setInterpolator(decelerateInterpolator).setStartDelay(300);
        animate(mTitle).translationY(0).setDuration(TOOLBAR_ANIMATE_TIME).setInterpolator(decelerateInterpolator).setStartDelay(400);
        animate(mLikeItem.getActionView()).translationY(0).setDuration(TOOLBAR_ANIMATE_TIME)
                .setInterpolator(decelerateInterpolator)
                .setStartDelay(500)
                .setListener(onAnimationEnd);
    }

}
