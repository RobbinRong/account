package com.robbin.rong.account.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.robbin.rong.account.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RefreshListView extends ListView implements AbsListView.OnScrollListener ,AdapterView.OnItemClickListener{
	private static final int STATE_PULL_REFRESH = 0;// 下拉刷新
	private static final int STATE_RELEASE_REFRESH = 1;// 松开刷新
	private static final int STATE_REFRESHING = 2;// 正在刷新
	private int mCurrrentState = STATE_PULL_REFRESH;// 当前状态
	private TextView tvTitle;
	private TextView tvTime;
	private ImageView ivArrow;
	private ProgressBar pbProgress;
	private int startY=-1;
	private View mHeaderView;
	private View mFooterView;
	private RotateAnimation animUp;
	private RotateAnimation animDown;
	private int mHeaderViewHeight;
	private int mFooterViewHeight;
	private  Context context;
	private LoadingFooter mLoadingFooter;

	public RefreshListView(Context context) {
		super(context);
		initHeaderView(context);
	}
	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView(context);
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView(context);
	}




	private void initHeaderView(Context context) {
		this.context=context;
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);

		mHeaderView.measure(0, 0);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);//隐藏头布局
		tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
		ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
		pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);
		initAnim();
		initFooterView();

	}
		private void  initFooterView(){
			mLoadingFooter = new LoadingFooter(getContext());

			//addFooterView(mLoadingFooter.getView());
			mFooterView=mLoadingFooter.getView();
			this.addFooterView(mFooterView);
			measure(0,0);
			mFooterViewHeight=getMeasuredHeight();
			//mFooterView.setPadding(0,-mFooterViewHeight,0,0);
			mFooterView.setPadding(0,-mFooterViewHeight,0,0);
			this.setOnScrollListener(this);

		}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {


		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				startY= (int) ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				if (startY==-1){
					startY= (int) ev.getRawY();
				}
				int endY= (int) ev.getRawY();
				int dy=endY-startY;
				if(dy>0&&getFirstVisiblePosition()==0){

					int padding = dy - mHeaderViewHeight;// 计算padding
					mHeaderView.setPadding(0,padding,0,0);
					if (padding > 0 && mCurrrentState != STATE_RELEASE_REFRESH) {// 状态改为松开刷新
						mCurrrentState = STATE_RELEASE_REFRESH;
						refreshState();
					} else if (padding < 0 && mCurrrentState != STATE_PULL_REFRESH) {// 改为下拉刷新状态
						mCurrrentState = STATE_PULL_REFRESH;
						refreshState();
					}
					super.onTouchEvent(ev);
					return  true;
				}

				break;
			case MotionEvent.ACTION_UP:
				startY=-1;

				if (mCurrrentState == STATE_RELEASE_REFRESH) {
					mCurrrentState = STATE_REFRESHING;// 正在刷新
					mHeaderView.setPadding(0, 0, 0, 0);// 显示
					refreshState();
				} else if (mCurrrentState == STATE_PULL_REFRESH) {
					mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);// 隐藏
				}

				break;



		}

		return super.onTouchEvent(ev);
	}

	private void refreshState(){
		switch (mCurrrentState){
			case STATE_PULL_REFRESH:
				tvTitle.setText("下拉刷新");
				ivArrow.setVisibility(VISIBLE);
				pbProgress.setVisibility(INVISIBLE);
				ivArrow.startAnimation(animDown);
				break;
			case STATE_RELEASE_REFRESH:
				tvTitle.setText("松开刷新");
				ivArrow.setVisibility(VISIBLE);
				pbProgress.setVisibility(INVISIBLE);
				ivArrow.startAnimation(animUp);
				break;
			case STATE_REFRESHING:
				startY=-1;



				tvTitle.setText("正在刷新");
				ivArrow.clearAnimation();
				ivArrow.setVisibility(INVISIBLE);
				pbProgress.setVisibility(VISIBLE);


				if (mListener != null) {
					mListener.onRefresh();
				}
				break;


		}
	}
	public void initAnim(){
		 animUp=new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		 animDown=new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animUp.setDuration(200);
		animDown.setDuration(200);
		animDown.setFillAfter(true);
		animUp.setFillAfter(true);
	}

	OnRefreshListener mListener;
	public void setOnRefreshListener(OnRefreshListener listener) {
		mListener = listener;
	}

	private boolean isLoadingMore;

	@Override
	public void onScrollStateChanged(AbsListView absListView, int i) {

		if(i==SCROLL_STATE_FLING||i==SCROLL_STATE_IDLE){
			if(getLastVisiblePosition()==(getCount()-1)&&!isLoadingMore){

				mFooterView.setPadding(0, 0, 0, 0);// 显示
				setSelection(getCount() - 1);// 改变listview显示位置

				isLoadingMore = true;
				mLoadingFooter.setState(LoadingFooter.State.Loading);

				if (mListener != null) {
					mListener.onLoadMore();
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView absListView, int i, int i1, int i2) {

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		onItemClickListener.onItemClick(adapterView,view, i-2, l);
	}

	public interface  OnRefreshListener{
		 void onRefresh();
		 void onLoadMore();
	}
	public void onRefreshComplete(boolean success){
		if (isLoadingMore) {// 正在加载更多...
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏脚布局
			isLoadingMore = false;
		}
		else{
		mCurrrentState = STATE_PULL_REFRESH;
		tvTitle.setText("下拉刷新");
		ivArrow.setVisibility(View.VISIBLE);
		//pbProgress.setVisibility(View.INVISIBLE);

		mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);// 隐藏

		if (success) {
			tvTime.setText("最后刷新时间:" + getCurrentTime());
		}
		}
	}
	public String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	OnItemClickListener onItemClickListener;
	@Override
	public void setOnItemClickListener(OnItemClickListener listener) {
		super.setOnItemClickListener(this);
		onItemClickListener=listener;
	}

}
