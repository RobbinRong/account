package com.robbin.rong.account;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

public class ImageActivity extends Activity {

    private ImageView code2;
    private BitmapUtils bitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        code2= (ImageView) findViewById(R.id.twoweima);
        String  code2Url = getIntent().getStringExtra("code2");
        bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(code2,code2Url);
    }

}
