package com.robbin.rong.account.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.robbin.rong.account.MainActivity;
import com.robbin.rong.account.R;

/**
 * Created by Administrator on 2016/4/5.
 */
public class BasePage {
    public FragmentActivity mActivity;
    public TextView tvTitle;
    public ImageButton btnMenu;
    public FrameLayout fl_content;
    public View mRootView;
    public BasePage(FragmentActivity mActivity) {
        this.mActivity = mActivity;
        initViews();
    }
    public void initViews(){
        mRootView=View.inflate(mActivity, R.layout.baselayout,null);
        tvTitle= (TextView) mRootView.findViewById(R.id.tv_title);
        btnMenu= (ImageButton) mRootView.findViewById(R.id.btn_menu);
        fl_content = (FrameLayout) mRootView.findViewById(R.id.fl_content);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSlidingMenu();
            }
        });
    }
    public void initData(){

    }
    public  void setSlidingMenuEnable(boolean is){
        MainActivity activity= (MainActivity)mActivity;
        if(is){
           // activity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }
        else{
            //activity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
    private void toggleSlidingMenu() {
        MainActivity mainUi= (MainActivity) mActivity;
        //mainUi.getSlidingMenu().toggle();

    }
}
