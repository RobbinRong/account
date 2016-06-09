package com.zhy.sample.folderlayout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * 
 * @author zhy http://blog.csdn.net/lmj623565791/
 */
public class MainActivity extends ListActivity
{

	private String[] mTitles = { "Matrix_setPolyToPoly",
			"MatrixPolyToPolyWithShadowActivity", "SimpleUseActivity",
			"FoldLayoutActivity", "DrawerLayoutSampleActivity" ,"SlidingPanelLayoutSampleActivity"};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getListView().setAdapter(
				new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, mTitles));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = null;

		switch (position)
		{
		case 0:
			intent = new Intent(this, MatrixPolyToPolyActivity.class);
			break;
		case 1:
			intent = new Intent(this, MatrixPolyToPolyWithShadowActivity.class);
			break;
		case 2:
			intent = new Intent(this, SimpleUseActivity.class);
			break;
		case 3:
			intent = new Intent(this, FoldLayoutActivity.class);
			break;
		case 4:
			intent = new Intent(this, DrawerLayoutSampleActivity.class);
			break;
		case 5:
			intent = new Intent(this, SlidingPanelLayoutSampleActivity.class);
			break;
		}

		if (intent != null)
			startActivity(intent);
	}

}
