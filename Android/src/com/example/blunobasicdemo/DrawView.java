package com.example.blunobasicdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	
    private int mLastX, mLastY;
    private int mCurrX, mCurrY;
	private Bitmap mBitmap;
	private Paint mPaint;
	
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setStrokeWidth(6);
	}


    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        
        int width = getWidth();
        int height = getHeight();
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);  
        }
        
        Canvas tmpCanvas = new Canvas(mBitmap);
        tmpCanvas.drawLine(mLastX, mLastY, mCurrX, mCurrY, mPaint);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }
    
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  

        mLastX = mCurrX;
        mLastY = mCurrY;
        mCurrX = (int) event.getX();
        mCurrY = (int) event.getY();

        switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN:
            mLastX = mCurrX;
            mLastY = mCurrY;
            break;
        default:  
            break;
        }  

        invalidate();  
        return true;
    }  
    
}
