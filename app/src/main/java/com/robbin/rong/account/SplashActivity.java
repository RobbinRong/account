package com.robbin.rong.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.oneapm.agent.android.OneApmAgent;
import com.robbin.rong.account.utils.PrefUtil;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;
public class SplashActivity extends ActionBarActivity {

    private RelativeLayout rlRoot;
    private HttpUtils httpUtils;
    private SharedPreferences sp;
    private static  final  String URL = "https://route.showapi.com/582-2?showapi_appid=19588&showapi_sign=d650ea2058644774a544a18430e8cedd&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OneApmAgent.init(this.getApplicationContext()).setToken("B4424E2445BEEA31A722CCA880752C2253").start();
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,"57a050fc67e58e2a970001fb","wandoujia"));

      /*  NewRelic.withApplicationToken(
                "AAea3b01af3ead1056c9b04336f7104ca0849869bf"
        ).start(this.getApplication());
*/


//        OneApmAgent.init(this.getApplicationContext()).setToken("7C5B856E7C5EF86CEC27811E478D1A4F53").start();
        httpUtils=new HttpUtils();
        sp=getSharedPreferences("account_data",MODE_PRIVATE);
        rlRoot= (RelativeLayout) findViewById(R.id.rl_root);
        getdataFromHttp();
        startAnim();
    }

    private void getdataFromHttp() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String d = format.format(date);
        String u=URL;
        u += "showapi_timestamp=" + d + "&";
        u += "key=&page=1&";
        u += "typeId=19";
        HttpUtils httpUtils=new HttpUtils();
        Log.e("splash","getDataFromHttp: "+u);
        httpUtils.send(HttpRequest.HttpMethod.GET, u, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("splash","getDataFromHttp: "+result);
                sp.edit().putString("first_data",result).commit();
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    public void startAnim(){

        AnimationSet animationSet=new AnimationSet(false);
        RotateAnimation rotateAnimation=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlRoot.startAnimation(animationSet);

    }

    private void jumpNextPage() {
        if(!(PrefUtil.getBoolean(this,"is_user_guide_showed",false))){
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));

        }
        else{
            startActivity(new Intent(SplashActivity.this,MainActivity.class));

        }

        finish();

    }

}
