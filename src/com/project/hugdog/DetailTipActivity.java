package com.project.hugdog;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DetailTipActivity extends Activity implements
		OnTouchListener {
	private String tipNumber;
	private ImageView imgTip;
	private ScaleGestureDetector sgd;
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	static final int None = 0;
	static final int Drag = 1;
	static final int Zoom = 2;
	int mode = None;
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDistance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_tip);
		tipNumber = getIntent().getStringExtra("tip");
		imgTip = (ImageView) findViewById(R.id.imgTip);
		chooseTip();
		imgTip.setOnTouchListener(this);
		
		/*
		 * Toast.makeText(getApplicationContext(), tipNumber, Toast.LENGTH_LONG)
		 * .show();
		 */

		// /zoom
		sgd = new ScaleGestureDetector(this, new ScaleListener());

		// end zoom
	}

	private void chooseTip() {
		if (tipNumber.equals("1")) {
			imgTip.setImageResource(R.drawable.nosebleed);
		} else if (tipNumber.equals("2")) {
			imgTip.setImageResource(R.drawable.feed);
		} else if (tipNumber.equals("3")) {
			imgTip.setImageResource(R.drawable.dont);
		} else if (tipNumber.equals("4")) {
			imgTip.setImageResource(R.drawable.tip4);
		} else if (tipNumber.equals("5")) {
			imgTip.setImageResource(R.drawable.tip5);
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		sgd.onTouchEvent(event);
		ImageView view = (ImageView) v;
		
		switch(event.getAction()&MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(),event.getY());
			mode = Drag;
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = None;
			break;
			
		case MotionEvent.ACTION_MOVE:
			if(mode == Drag){
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
				//matrix.postTranslate(start.x -event.getX()  ,  start.y -event.getY() );
			}
			/* else if (mode == Zoom) {
	                float newDist = getDistance(event);
	                if (newDist > 10f) {
	                    matrix.set(savedMatrix);
	                    float scale = newDist / oldDistance;
	                    matrix.postScale(scale, scale, mid.x, mid.y);
	                }
	            }
	            */
			break;
		}
		
		view.setImageMatrix(matrix);
		
		return true;
	}

	

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float scaleFactor = detector.getScaleFactor();
			matrix.postScale(scaleFactor, scaleFactor);

			return true;
		}
	
		
	}

}
