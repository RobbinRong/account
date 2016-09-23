package com.robbin.rong.account.base;

import android.app.Activity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.TextView;


import com.robbin.rong.account.R;
import com.robbin.rong.account.adapter.AccountAdapter;
import com.robbin.rong.account.Global.GlobalConstant;
import com.robbin.rong.account.ImageActivity;
import com.robbin.rong.account.domain.Account;
import com.robbin.rong.account.domain.AccountCateory;
import com.robbin.rong.account.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Administrator on 2016/4/7.
 */
public class TabAccountDetailPager extends BaseMenuDetailPager {

    private AccountAdapter accountAdapter;
    private AccountCateory.Child mTabData;
    private int firstItem;
    private String url;
    private RefreshListView lvList;
    private int page = 1;
    private ArrayList<Account.Content> contentlist;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                contentlist.clear();
                getDataFromServer(false);
                Log.e("rjb", "onRefresh: 下拉刷新");
                lvList.onRefreshComplete(true);
            }
            if(msg.what==2){
                page++;
                getDataFromServer(true);
                lvList.onRefreshComplete(true);// 收起加载更多的布局
            }

        }
    };

    public TabAccountDetailPager(Activity activity, AccountCateory.Child child) {
        super(activity);
        mTabData = child;
        url = GlobalConstant.ACCOUNT__URL;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        ViewUtils.inject(this, view);
        lvList = (RefreshListView) view.findViewById(R.id.lv_list1);
        lvList.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Message obtain = Message.obtain();
                            obtain.what = 1;
                            handler.sendMessage(obtain);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Message obtain = Message.obtain();
                            obtain.what = 2;
                            handler.sendMessage(obtain);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();



            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, ImageActivity.class);
                intent.putExtra("code2", contentlist.get(i + 1).code2img);
                mActivity.startActivity(intent);
                changeReadState(view);
            }
        });
        return view;
    }

    private void changeReadState(View view) {
        TextView pub_num = (TextView) view.findViewById(R.id.pub_num);
        TextView wei_num = (TextView) view.findViewById(R.id.wei_num);
        pub_num.setTextColor(Color.GRAY);
        wei_num.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
        getDataFromServer(false);

    }

    private void getDataFromServer(final boolean isMore) {
        final boolean is=isMore;
        String u =url;
        u+=page+"&showapi_appid=19588";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String d = format.format(date);
        u += "&showapi_timestamp=" + d;
        u += "&&type1_id=&type2_id=" + mTabData.id;
        u += "&showapi_sign=" + GlobalConstant.SHOWAPI_SIGN;
        Log.e("", "----------------getDataFromServer: "+u);

        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, u, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("rjb", "onSuccess: "+result );
                parseData(result,is);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });


    }

    public void parseData(String result,boolean isMore) {
        Gson gson=new Gson();
        Account account = gson.fromJson(result, Account.class);
        if(isMore){
            contentlist.addAll(account.showapi_res_body.pagebean.contentlist);
            accountAdapter.notifyDataSetChanged();
        }
        else {
         if(null!=account.showapi_res_body.pagebean.contentlist){
             contentlist=account.showapi_res_body.pagebean.contentlist;
             accountAdapter = new AccountAdapter(contentlist, mActivity);
             lvList.setAdapter(accountAdapter);
         }


        }


    }

}
