package com.robbin.rong.account.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.robbin.rong.account.R;
import com.robbin.rong.account.domain.AccountCateory;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/6.
 */
//一级分类
public class AccountDetailPager extends BaseMenuDetailPager{
    @ViewInject(R.id.vp_tabdetail)
    private ViewPager viewPager;
    private ArrayList<TabAccountDetailPager> list;
    private  ArrayList<AccountCateory.Child> tabList;
    @ViewInject(R.id.tab_indicator)
    private TabPageIndicator tabPageIndicator;
    public AccountDetailPager(Activity activity, ArrayList<AccountCateory.Child> tabList) {
        super(activity);
        this.tabList=tabList;
    }

    @Override
    public View initViews() {
        View view=View.inflate(mActivity, R.layout.tab_artical_detail,null);
        ViewUtils.inject(this,view);
        return view;
    }
   /* @OnClick(R.id.btn_next)
    public  void next(View view){
        int n=viewPager.getCurrentItem();
        viewPager.setCurrentItem(++n);

    }*/
    @Override
    public void initData() {
        list=new ArrayList<TabAccountDetailPager>();
        for (int i=0;i<tabList.size();i++){
            TabAccountDetailPager tabAccountDetailPager=new TabAccountDetailPager(mActivity,tabList.get(i));
            list.add(tabAccountDetailPager);
        }
        MenuDetaiAdapter adapter= new MenuDetaiAdapter();
        viewPager.setAdapter(adapter);
        tabPageIndicator.setViewPager(viewPager);
        tabPageIndicator.setVisibility(View.VISIBLE);
    }


    private class MenuDetaiAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position).name;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabAccountDetailPager tabNewsDetailPager=list.get(position);
            container.addView(tabNewsDetailPager.mRootView);
            tabNewsDetailPager.initData();
            return tabNewsDetailPager.mRootView ;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
