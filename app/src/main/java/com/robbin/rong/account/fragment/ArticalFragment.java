package com.robbin.rong.account.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.robbin.rong.account.R;
import com.robbin.rong.account.adapter.ArticleAdapter;
import com.robbin.rong.account.ArticleWeb;
import com.robbin.rong.account.domain.Artical;
import com.robbin.rong.account.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/1.
 */
@SuppressLint("ValidFragment")

public class ArticalFragment extends Fragment {

    private RefreshListView listView;
    private String url = "https://route.showapi.com/582-2?showapi_appid=19588&showapi_sign=d650ea2058644774a544a18430e8cedd&";
    private String typeId;
    private View rootView;
    private Artical artical;
    private ArrayList<Artical.Content> contentlist;
    private Context context;
    private ArticleAdapter articleAdapter;
    private int page=1;
    private  int firstItem=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                contentlist.clear();
                artical=null;
                getDataFromHttp(false);
                Log.e("rjb", "onRefresh: 下拉刷新");
                listView.onRefreshComplete(true);
            }

        }
    };
    public ArticalFragment(String typeId, Context context   ) {
        this.typeId=typeId;
        this.context=context;

    }

    public void getDataFromHttp(final boolean isMore){
        final boolean is=isMore;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String d = format.format(date);
        String u=url;
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
        artical = gson.fromJson(result, Artical.class);
        if(isMore){
            contentlist.addAll(artical.showapi_res_body.pagebean.contentlist);
        }
        else {
            contentlist=artical.showapi_res_body.pagebean.contentlist;
        }
        articleAdapter = new ArticleAdapter(contentlist, context);
        listView.setAdapter(articleAdapter);

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
            }
        });
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message obtain = Message.obtain();
                        obtain.what=1;
                        handler.sendMessage(obtain);

                    }
                }).start();

            }

            @Override
            public void onLoadMore() {
                page++;
                getDataFromHttp(true);

                firstItem=listView.getFirstVisiblePosition();
                listView.setSelection(15);
                articleAdapter.notifyDataSetInvalidated();

                listView.onRefreshComplete(false);// 收起加载更多的布局

            }
        });
        return rootView;
    }


}
