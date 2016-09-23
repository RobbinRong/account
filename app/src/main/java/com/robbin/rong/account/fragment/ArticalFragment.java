package com.robbin.rong.account.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.robbin.rong.account.ArticleWeb;
import com.robbin.rong.account.R;
import com.robbin.rong.account.adapter.ArticleAdapter;
import com.robbin.rong.account.domain.Artical;
import com.robbin.rong.account.view.RefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/1.
 */
@SuppressLint("ValidFragment")

public class ArticalFragment extends Fragment {
    private boolean isFirst=true;
    private RefreshListView listView;
    private static  final  String URL = "https://route.showapi.com/582-2?showapi_appid=19588&showapi_sign=d650ea2058644774a544a18430e8cedd&";
    private String typeId;
    private View rootView;
    private ArrayList<Artical.Content> contentlist;
    private Context context;
    private ArticleAdapter articleAdapter;
    private int page=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                contentlist.clear();
               // artical=null;
                getDataFromHttp(false);
                Log.e("rjb", "onRefresh: 下拉刷新");
                listView.onRefreshComplete(true);
            }
            if(msg.what==2){
                page++;
                getDataFromHttp(true);
                listView.onRefreshComplete(false);// 收起加载更多的布局
            }

        }
    };
    public ArticalFragment(String typeId, Context context   ) {
        this.typeId=typeId;
        this.context=context;
    }

    public void getDataFromHttp(final boolean isMore){
        if (isFirst){
            SharedPreferences sp=context.getSharedPreferences("account_data",Context.MODE_PRIVATE);
            String first_data = sp.getString("first_data", null);
            if(!TextUtils.isEmpty(first_data)){
                if(listView==null){
                    listView= (RefreshListView)LayoutInflater.from(context).inflate(R.layout.artical_fragment,null).findViewById(R.id.lv_List);
                }
                Log.e("first", "getDataFromHttp: "+first_data);
                parseData(first_data,false);
                isFirst=false;
                sp.edit().remove("first_data").commit();
                return;
            }

        }
        final boolean is=isMore;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String d = format.format(date);
        String u=URL;
        u += "showapi_timestamp=" + d + "&";
        u += "key=&page="+page+"&";
        u += "typeId="+typeId;
        HttpUtils httpUtils=new HttpUtils();
        Log.e("rjb>>>>>>>","getDataFromHttp: "+u);
        httpUtils.send(HttpRequest.HttpMethod.GET, u, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result,is);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void parseData(String result,boolean isMore) {
        Gson gson=new Gson();
        Artical artical = gson.fromJson(result, Artical.class);
        if(isMore){
            contentlist.addAll(artical.showapi_res_body.pagebean.contentlist);
            articleAdapter.notifyDataSetChanged();

        }
        else {
            if(null!=artical.showapi_res_body.pagebean.contentlist){
                contentlist=artical.showapi_res_body.pagebean.contentlist;
                articleAdapter = new ArticleAdapter(contentlist, context);
                listView.setAdapter(articleAdapter);

            }

        }

    }
    private void changeReadState(View view) {
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setTextColor(Color.GRAY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.artical_fragment,null);
        listView= (RefreshListView) rootView.findViewById(R.id.lv_List);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("rjb", "onItemClick: "+position );
                Artical.Content content = contentlist.get(position+1);
                Intent intent = new Intent(context, ArticleWeb.class);
                intent.putExtra("url", content.url);
                Log.e("rjb", "onItemClick: "+position+"=============="+content.url);
                context.startActivity(intent);
                changeReadState(view);

            }
        });
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
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
        return rootView;
    }


}
