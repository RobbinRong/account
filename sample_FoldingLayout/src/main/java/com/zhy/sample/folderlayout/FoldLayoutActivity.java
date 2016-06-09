package com.zhy.sample.folderlayout;

import android.app.Activity;
import android.os.Bundle;

import com.zhy.view.FoldLayout;
/**
 * 
 * @author zhy http://blog.csdn.net/lmj623565791/
 */
public class FoldLayoutActivity extends Activity
{
	private FoldLayout mFoldLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fold);

		mFoldLayout = (FoldLayout) findViewById(R.id.id_fold_layout);
	
		/*mFoldLayout.post(new Runnable()
		{

			@SuppressLint("NewApi")
			@Override
			public void run()
			{
				ObjectAnimator.ofFloat(mFoldLayout, "factor", 1, 0, 1)
						.setDuration(5000).start();
			}
		});*/

	}
}
