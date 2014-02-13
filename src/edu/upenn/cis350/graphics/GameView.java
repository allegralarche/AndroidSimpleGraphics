package edu.upenn.cis350.graphics;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	
	private ArrayList<Float> lines = new ArrayList<Float>();
	private Bitmap image;
	private Paint p;
	private int unicornx = 0;

	public GameView(Context context) {
		super(context);
		init();
	}

	public GameView(Context context, AttributeSet as) {
		super(context, as);
		init();
	}

	private void init() {
		setBackgroundResource(R.drawable.space);
		p = new Paint();
		image = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn);
		image = Bitmap.createScaledBitmap(image, 150, 150, false);
		System.out.println("at least i initialized?");
		BackThread bt = new BackThread();
		bt.execute();
	}


	public void onDraw(Canvas c) {
		/* called each time this View is drawn */
		c.drawBitmap(image, unicornx, 100, p);
		p.setColor(Color.RED);
		p.setStrokeWidth(5);
		for(int i = 0; i < lines.size()-3; i+=2) {
			c.drawLine(lines.get(i), lines.get(i+1), lines.get(i+2), lines.get(i+3), p);
		}
	}
	
	
	public boolean onTouchEvent(MotionEvent e) {
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			lines.add(e.getX());
			lines.add(e.getY());
			return true;
		}
		else if(e.getAction() == MotionEvent.ACTION_MOVE) {
			lines.add(e.getX());
			lines.add(e.getY());
			invalidate();
			return true;
		}
		else if(e.getAction() == MotionEvent.ACTION_UP) {
			lines = new ArrayList<Float>();
			invalidate();
			return true;
		}
		return false;
	}
	
	private class BackThread extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				System.out.println("made it to thread");
				Thread.sleep(20);
			} catch (InterruptedException e) {
				
			}
			return null;
		}
		@SuppressWarnings("unused")
		protected void onPostExecute(){
			System.out.println("made it to postexecute");
			unicornx+=10;
			invalidate();
			this.execute();
		}
	}

}