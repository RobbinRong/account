package com.robbin.rong.account.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.robbin.rong.account.Global.GlobalConstant;
import com.robbin.rong.account.R;
import com.robbin.rong.account.domain.ArticalCategory;
import com.robbin.rong.account.fragment.ArticalFragment;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.TabPageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/1.
 */
public class ArticalPageImpl extends BasePage implements ViewPager.OnPageChangeListener{
    private static  final String TAG="ArticalPageImpl>>>";
    private TabPageIndicator tabPageIndicator;
    private ViewPager viewPager;
    private ArrayList<ArticalFragment> articalFragments=new ArrayList<>();
    private View content;
    private ArrayList<ArticalCategory.Type> typeList;
    private ImageButton btn_next;
    private MyAdapter myAdapter;

    public ArticalPageImpl(FragmentActivity mActivity) {
        super(mActivity);
        initData();
    }

    @Override
    public void initData() {
        btnMenu.setVisibility(View.INVISIBLE);
        tvTitle.setText("精选文章");
        content= LayoutInflater.from(mActivity).inflate(R.layout.tab_artical_detail,null);
        viewPager= (ViewPager) content.findViewById(R.id.vp_tabdetail);
        tabPageIndicator= (TabPageIndicator) content.findViewById(R.id.tab_indicator);
        setSlidingMenuEnable(false);
        getDataFromServer();
        btn_next = (ImageButton) content.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n=viewPager.getCurrentItem();
                Log.e(TAG, "onClick: "+n);
                viewPager.setCurrentItem(++n);
            }
        });
    }


    private void getDataFromServer() {
        HttpUtils httpUtils=new HttpUtils();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        String url= GlobalConstant.ARTICAL_CAGEGORY_URL+"&showapi_timestamp="+time+"&showapi_sign="+GlobalConstant.SHOWAPI_SIGN;
        Log.e(TAG, "getDataFromServer: "+url );
        if(typeList!=null){
            return;
        }
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                ArticalCategory articalCategory = (ArticalCategory) parseData(result);
                Log.e(TAG, "onSuccess: "+articalCategory.toString());

            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });

    }

    private ArticalCategory parseData(String result) {
        final Gson gson=new Gson();
        ArticalCategory articalCategory = gson.fromJson(result, ArticalCategory.class);
        Log.e(TAG, "parseData: "+articalCategory.showapi_res_body.typeList.toString());
        typeList = articalCategory.showapi_res_body.typeList;
        for(int i = 0; i< typeList.size(); i++){
            articalFragments.add(new ArticalFragment(typeList.get(i).id,mActivity));
        }
        articalFragments.get(0).getDataFromHttp(false);
        FragmentManager supportFragmentManager = mActivity.getSupportFragmentManager();
        myAdapter = new MyAdapter(supportFragmentManager, articalFragments);
        viewPager.setAdapter(myAdapter);
        tabPageIndicator.setViewPager(viewPager);
        tabPageIndicator.setVisibility(View.VISIBLE);
        tabPageIndicator.setOnPageChangeListener(this);
        fl_content.removeAllViews();
        fl_content.addView(content);
        return articalCategory;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        String typeId=typeList.get(position).id;
        articalFragments.get(position).getDataFromHttp(false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class  MyAdapter extends FragmentPagerAdapter{
        private ArrayList<ArticalFragment> articalFragments;
        public MyAdapter(FragmentManager fm,ArrayList<ArticalFragment> articalFragments) {
            super(fm);
            this.articalFragments=articalFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.articalFragments.get(position);
        }

        @Override
        public int getCount() {
            return this.articalFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return typeList.get(position).name;
        }
    }
}
