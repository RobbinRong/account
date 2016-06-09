package com.robbin.rong.account;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class ArticleWeb extends Activity {
    private WebView articleWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web);
        articleWebView= (WebView) findViewById(R.id.article_web_view);
        String url=getIntent().getStringExtra("url");
        articleWebView.getSettings().setJavaScriptEnabled(true);
        articleWebView.loadUrl(url);
    }
}
