package com.robbin.rong.account;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.robbin.rong.account.utils.PrefUtil;

import java.util.ArrayList;


public class GuideActivity extends ActionBarActivity {

    private static  final int[] imageIds=new int[]{R.drawable.guide_1,
            R.drawable.guide_2,R.drawable.guide_3};
    private ArrayList<ImageView> imageViews=new ArrayList<ImageView>();
    private ViewPager vpGuide;
    private Button btn_guide;
    private LinearLayout llPointFroup;
    private int mPointWidth;
    private View viewRedPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vpGuide= (ViewPager) findViewById(R.id.vp_guide);
        llPointFroup= (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint=findViewById(R.id.redpoint);
        btn_guide= (Button) findViewById(R.id.btn_guide);
        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtil.setBoolean(GuideActivity.this,"is_user_guide_showed",true);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();

            }
        });

        initViews();
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.setOnPageChangeListener(new GuideListener());
    }
    void initViews(){
    for (int imagId:imageIds){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(imagId);
        imageViews.add(imageView);
    }

        for (int i=0;i<imageIds.length;i++){
            View view=new View(GuideActivity.this);
            view.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(10,10);
            if(i>0){
                params.leftMargin=10;
            }
            view.setLayoutParams(params);
            llPointFroup.addView(view);
        }
        llPointFroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mPointWidth= llPointFroup.getChildAt(1).getLeft()-llPointFroup.getChildAt(0).getLeft();
                llPointFroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }

    class GuideListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

            int left= (int) (i*mPointWidth+v*mPointWidth);
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
            params.leftMargin=left;
            viewRedPoint.setLayoutParams(params);

        }

        @Override
        public void onPageSelected(int i) {
            if(i==imageIds.length-1){
                btn_guide.setVisibility(View.VISIBLE);
            }
            else {
                btn_guide.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
