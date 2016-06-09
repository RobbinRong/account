package com.robbin.rong.account.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.robbin.rong.account.R;


/**
 * Created by storm on 14-4-12.
 */
public class LoadingFooter {
    protected View mLoadingFooter;


    TitanicTextView mTitanicText;

    private Titanic mTitanic;

    protected State mState = State.Idle;

    public static enum State {
        Idle, Loading
    }

    public LoadingFooter(Context context) {
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.loading_footer, null);
        mLoadingFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 屏蔽点击
            }
        });
        mTitanicText = (TitanicTextView) mLoadingFooter.findViewById(R.id.tv_titanic);
        mTitanic = new Titanic();
        mTitanic.start(mTitanicText);
        setState(State.Idle);
    }

    public View getView() {
        return mLoadingFooter;
    }

    public State getState() {
        return mState;
    }

    public void setState(final State state, long delay) {
        mLoadingFooter.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(state);
            }
        }, delay);
    }

    public void setState(State status) {
        if (mState == status) {
            return;
        }
        mState = status;
        mLoadingFooter.setVisibility(View.VISIBLE);
        switch (status) {
            case Loading:
                mTitanicText.setVisibility(View.VISIBLE);
                break;
            default:
                mLoadingFooter.setVisibility(View.GONE);
                break;
        }
    }
}
