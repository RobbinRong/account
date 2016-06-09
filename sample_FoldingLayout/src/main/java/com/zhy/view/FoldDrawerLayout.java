package com.zhy.view;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author zhy http://blog.csdn.net/lmj623565791/
 */
public class FoldDrawerLayout extends DrawerLayout
{
	private static final String TAG = "DrawerFoldLayout";

	public FoldDrawerLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();

		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			final View child = getChildAt(i);
			if (isDrawerView2(child))
			{
				Log.e(TAG, "at" + i);
				FoldLayout foldlayout = new FoldLayout(getContext());
				foldlayout.setAnchor(1);
				removeView(child);
				foldlayout.addView(child);
				ViewGroup.LayoutParams layPar = child.getLayoutParams();
				addView(foldlayout, i, layPar);
			}

		}
		setDrawerListener(new DrawerListener()
		{

			@Override
			public void onDrawerStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{

				if (drawerView instanceof FoldLayout)
				{
					FoldLayout foldLayout = ((FoldLayout) drawerView);
					Log.e(TAG, "slideOffset = " + slideOffset);
					foldLayout.setFactor(slideOffset);
				}

			}

			@Override
			public void onDrawerOpened(View arg0)
			{

			}

			@Override
			public void onDrawerClosed(View arg0)
			{

			}
		});

	}

	boolean isDrawerView2(View child)
	{
		final int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
		final int absGravity = GravityCompat.getAbsoluteGravity(gravity,
				ViewCompat.getLayoutDirection(child));
		return (absGravity & (Gravity.LEFT | Gravity.RIGHT)) != 0;
	}

}
