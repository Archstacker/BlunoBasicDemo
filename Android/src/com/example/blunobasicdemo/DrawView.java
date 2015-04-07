package com.example.blunobasicdemo;

import java.util.ArrayList;
import java.util.Iterator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
    
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    
    private float prevX,prevY;
    private ArrayList<Double> saveData;

    public DrawView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    //setup drawing
    private void setupDrawing(){

        //prepare for drawing and setup paint stroke properties
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(6);
        drawPaint.setStyle(Paint.Style.STROKE);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        saveData = new ArrayList<Double>();
    }
    
    //size assigned to view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }
    
    //draw the view - will be called after touch event
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }
    
    //register user touches as drawing action
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            saveData.clear();
            prevX = touchX;
            prevY = touchY;
            drawCanvas.drawColor(0, Mode.CLEAR);
            drawPath.moveTo(touchX, touchY);
            break;
        case MotionEvent.ACTION_MOVE:
            saveData.add(180*Math.atan2(touchY-prevY,touchX-prevX)/Math.PI);
            prevX = touchX;
            prevY = touchY;
            drawPath.lineTo(touchX, touchY);
            break;
        case MotionEvent.ACTION_UP:
            saveData.add(180*Math.atan2(touchY-prevY,touchX-prevX)/Math.PI);
            drawPath.lineTo(touchX, touchY);
            drawCanvas.drawPath(drawPath, drawPaint);
            drawPath.reset();
            Iterator<Double> i=saveData.iterator();
            while(i.hasNext()){
                Log.d("POS",i.next()+"");
            }
            break;
        default:
            return false;
        }
        //redraw
        invalidate();
        return true;
        
    }
    
}
