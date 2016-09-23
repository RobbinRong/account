package com.robbin.rong.account.base;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.robbin.rong.account.Global.GlobalConstant;
import com.robbin.rong.account.MainActivity;
import com.robbin.rong.account.SearchActivity;
import com.robbin.rong.account.domain.AccountCateory;
import com.robbin.rong.account.fragment.LeftMenuFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/5.
 */
public class AccountPage extends BasePage {
    private static  final  String TAG="rjb --  Account>>";
    private ArrayList<BaseMenuDetailPager> mPagers;
    private boolean isOpen=false;
    public AccountPage(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        tvTitle.setText("热门公众号");
        setSlidingMenuEnable(true);
        ((MainActivity)mActivity).mDrawerLayout.setDrawerLockMode(DrawerLayout.STATE_IDLE);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    ((MainActivity)mActivity).mDrawerLayout.closeDrawer(GravityCompat.START);
                    isOpen=false;
                }
                else {
                    ((MainActivity)mActivity).mDrawerLayout.openDrawer(GravityCompat.START);
                    isOpen=true;
                }

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mActivity, SearchActivity.class);
                intent.putExtra("iswhat","1");
                mActivity.startActivity(intent);

            }
        });
        getDataFromServer();
    }

    private String getDataFromServer() {
        HttpUtils httpUtils=new HttpUtils();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        String url=GlobalConstant.CATEGORIES_URL+"&showapi_timestamp="+time+"&showapi_sign="+GlobalConstant.SHOWAPI_SIGN;
        Log.e(TAG, "getDataFromServer: "+url );
        HttpHandler<String> send = httpUtils.send(HttpRequest.HttpMethod.GET,url , new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                 parseData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e(TAG, "onFailure: "+msg );
            }
        });
        return  null;
    }

    private void parseData(String result) {
        Gson gson=new Gson();
        AccountCateory  accountCateory =gson.fromJson(result, AccountCateory.class);
        Log.e(TAG, "parseData: "+accountCateory.toString());
        if(TextUtils.isEmpty(accountCateory.showapi_res_error)){

            MainActivity mainUi= (MainActivity) mActivity;
            LeftMenuFragment lmf= mainUi.getLeftFragment();
            lmf.setData(accountCateory);
            mPagers = new ArrayList<BaseMenuDetailPager>();
            ArrayList<AccountCateory.Alt> allList = accountCateory.showapi_res_body.allList;
            for(int i=0;i<allList.size();i++){
                AccountDetailPager accountDetailPager = new AccountDetailPager(mActivity, allList.get(i).childList);
                mPagers.add(accountDetailPager);
            }

            setCurrentDetaiPager(0);

        }



    }

    public void setCurrentDetaiPager(int i) {
        BaseMenuDetailPager pager = mPagers.get(i);// 获取当前要显示的菜单详情页
        fl_content.removeAllViews();// 清除之前的布局
        fl_content.addView(pager.mRootView);//
        pager.initData();
    }
}
