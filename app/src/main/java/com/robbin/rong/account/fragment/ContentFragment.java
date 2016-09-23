package com.robbin.rong.account.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.robbin.rong.account.MainActivity;
import com.robbin.rong.account.R;
import com.robbin.rong.account.base.AccountPage;
import com.robbin.rong.account.base.ArticalPageImpl;
import com.robbin.rong.account.base.BasePage;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/3.
 */
public class ContentFragment extends BaseFragment {
    @ViewInject(R.id.rg_tab)
    private RadioGroup rgGroup;
    private ViewPager mViewPager;
    private ArrayList<BasePage> pageList;
    private AccountPage accountPage;
    private ArticalPageImpl articalPage;


    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragmentcontent, null);
        ViewUtils.inject(this, view);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_tab);
        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.td_artical);
        pageList = new ArrayList<BasePage>();
        articalPage = new ArticalPageImpl(mActivity);
        accountPage = new AccountPage(mActivity);
        pageList.add(articalPage);
        pageList.add(accountPage);

        mViewPager.setAdapter(new ContentAdapter());
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.td_artical:
                        mViewPager.setCurrentItem(0, false);
                        ((MainActivity)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        break;
                    case R.id.td_account:
                        mViewPager.setCurrentItem(1, false);
                        break;
                }
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageList.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pageList.get(0).initData();

    }

    private class ContentAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePage basePage = pageList.get(position);
            container.addView(basePage.mRootView);

            return basePage.mRootView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }

    public AccountPage getNewsCenterPager() {
        return (AccountPage) pageList.get(1);
    }
}
