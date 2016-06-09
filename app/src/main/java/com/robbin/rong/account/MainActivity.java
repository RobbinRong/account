package com.robbin.rong.account;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.robbin.rong.account.fragment.BaseFragment;
import com.robbin.rong.account.fragment.ContentFragment;
import com.robbin.rong.account.fragment.LeftMenuFragment;
import com.robbin.rong.account.view.BlurFoldingActionBarToggle;
import com.robbin.rong.account.view.FoldingDrawerLayout;


public class MainActivity extends FragmentActivity {

    private static  final  String FRAGMENT_LEFT_MENT="fragment_left_menu";
    private static  final  String FRAGMENT_CONTENT="fragment_content";
    public FoldingDrawerLayout mDrawerLayout;

    FrameLayout contentLayout;

    ImageView blurImage;

    private BlurFoldingActionBarToggle mDrawerToggle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout= (FoldingDrawerLayout) findViewById(R.id.drawer_layout);
        blurImage= (ImageView) findViewById(R.id.blur_image);
        contentLayout= (FrameLayout) findViewById(R.id.fl_content);
        mDrawerLayout.setScrimColor(Color.argb(100, 255, 255, 255));
        mDrawerToggle = new BlurFoldingActionBarToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                blurImage.setVisibility(View.GONE);
                blurImage.setImageBitmap(null);
            }
        };
        mDrawerToggle.setBlurImageAndView(blurImage, contentLayout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        replaceFragment(R.id.left_drawer, new  LeftMenuFragment(),FRAGMENT_LEFT_MENT);
        replaceFragment(R.id.fl_content, new ContentFragment(),FRAGMENT_CONTENT);

    }

    protected void replaceFragment(int viewId, BaseFragment fragment,String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment,tag).commit();
    }

    public LeftMenuFragment getLeftFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        LeftMenuFragment lmf= (LeftMenuFragment) fragmentManager.findFragmentByTag(FRAGMENT_LEFT_MENT);
        return  lmf;
    }
    public ContentFragment getContentFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        ContentFragment lmf= (ContentFragment) fragmentManager.findFragmentByTag(FRAGMENT_CONTENT);
        return  lmf;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
