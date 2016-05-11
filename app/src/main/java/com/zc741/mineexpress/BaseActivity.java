package com.zc741.mineexpress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityHelper;

public abstract class BaseActivity extends AppCompatActivity implements SlidingMenu.OnOpenedListener {
    private SlidingActivityHelper mHelper;
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mHelper = new SlidingActivityHelper(this);
        mHelper.onCreate(savedInstanceState);

        //这里借用了SlidingMenu的setBehindContentView方法来设置一个透明菜单
        View behindView = new View(this);
        behindView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        behindView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setBehindContentView(behindView);

        slidingMenu = getSlidingMenu();
        //设置阴影宽度为10个px
        slidingMenu.setShadowWidth(10);
        //设置阴影
        slidingMenu.setShadowDrawable(R.drawable.slide_shadow);
        //设置下面的布局，也就是我们上面定义的透明菜单离右边屏幕边缘的距离为0，也就是滑动开以后菜单会全屏幕显示
        slidingMenu.setBehindOffset(0);
        slidingMenu.setFadeDegree(0.7f);//滑动时边缘颜色的透明度 从 0.7f~0f 变化
        //菜单打开监听 菜单打开后要finish当前activity
        slidingMenu.setOnOpenedListener(this);

        //SlidingMenu.LEFT
        slidingMenu.setMode(SlidingMenu.LEFT);
        //边缘滑动 TOUCHMODE_MARGIN
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view != null) {
            return view;
        }
        return mHelper.findViewById(id);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mHelper.onSaveInstanceState(outState);
    }

    @Override
    public void setContentView(int id) {
        setContentView(getLayoutInflater().inflate(id, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mHelper.registerAboveContentView(view, params);
    }

    private void setBehindContentView(int id) {
        setBehindContentView(getLayoutInflater().inflate(id, null));
    }

    private void setBehindContentView(View view) {
        setBehindContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void setBehindContentView(View view, ViewGroup.LayoutParams layoutParams) {
        mHelper.setBehindContentView(view, layoutParams);
    }

    private SlidingMenu getSlidingMenu() {
        return mHelper.getSlidingMenu();
    }

    public void toggle() {
        mHelper.toggle();
    }

    public void showContent() {
        mHelper.showContent();
    }

    public void showMenu() {
        mHelper.showMenu();
    }

    public void showSecongaryMenu() {
        mHelper.showSecondaryMenu();
    }

    public void setSlidingActionBarEnable(boolean b) {
        mHelper.setSlidingActionBarEnabled(b);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean b = mHelper.onKeyUp(keyCode, event);
        if (b) return b;
        return super.onKeyUp(keyCode, event);
    }

    //菜单完全打开后结束当前Activity
    @Override
    public void onOpened() {
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.anim.slide_out_right);
    }
}
