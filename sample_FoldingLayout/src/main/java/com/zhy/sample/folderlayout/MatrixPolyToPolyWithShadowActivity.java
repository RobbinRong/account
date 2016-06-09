package com.zhy.sample.folderlayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
/**
 * 
 * @author zhy http://blog.csdn.net/lmj623565791/
 */
public class MatrixPolyToPolyWithShadowActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new PolyToPolyView(this));

	}

	class PolyToPolyView extends View
	{

		private Bitmap mBitmap;
		private Matrix mMatrix;
		
		private Paint mShadowPaint;
		private Matrix mShadowGradientMatrix;
		private LinearGradient mShadowGradientShader;

		public PolyToPolyView(Context context)
		{
			super(context);
			mBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.tanyan);
			mMatrix = new Matrix();

			mShadowPaint = new Paint();
			mShadowPaint.setStyle(Style.FILL);
			mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0,
					Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
			mShadowPaint.setShader(mShadowGradientShader);

			mShadowGradientMatrix = new Matrix();
			mShadowGradientMatrix.setScale(mBitmap.getWidth(), 1);
			mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
			mShadowPaint.setAlpha((int) (0.9*255));

		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			super.onDraw(canvas);
			canvas.save();
			float[] src = { 0, 0,//
					mBitmap.getWidth(), 0,//
					mBitmap.getWidth(), mBitmap.getHeight(),//
					0, mBitmap.getHeight() };
			float[] dst = { 0, 0,//
					mBitmap.getWidth(), 100,//
					mBitmap.getWidth(), mBitmap.getHeight() - 100,//
					0, mBitmap.getHeight() };
			mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);

			canvas.concat(mMatrix);
			canvas.drawBitmap(mBitmap, 0, 0, null);
			canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(),
					mShadowPaint);
			canvas.restore();

		}

	}

}
