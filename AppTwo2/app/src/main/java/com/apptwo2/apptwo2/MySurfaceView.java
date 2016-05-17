package com.apptwo2.apptwo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


// Creating a SurfaceView class
// http://android-er.blogspot.de/2014/03/simple-surfaceview-example.html


public class MySurfaceView extends View {

    //private SurfaceHolder surfaceHolder;
    private int time = 0;
    public float[] data_ = new float[3];


    Paint mPaint;


    public MySurfaceView(Context context) {
        super(context);
        init();

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }


    @Override
    public void onDraw(Canvas canvas){


        float ratio = getHeight()/20;

        System.out.print("onDraw is called!!!!!! \n");
        System.out.print(data_[0]+"\n");
        System.out.print(data_[1]+"\n");
        System.out.print(data_[2]+"\n");

        //if (time == getWidth()) {
        //    time = 0;
        //}


        mPaint.setColor(Color.GREEN);
        canvas.drawPoint(getWidth()/2, (float) ((getHeight() / 2) - ratio * data_[0]), mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawPoint(getWidth()/2, (float) ((getHeight() / 2) - ratio * data_[1]), mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawPoint(getWidth()/2, (float) ((getHeight() / 2) - ratio * data_[2]), mPaint);
        //time++;




        canvas.drawPoint(getWidth()/2, getHeight()/2, mPaint);

        super.onDraw(canvas);
        // paint.setTextSize(18f);
        // canvas.drawText("0", getWidth()-20f, getHeight()/2, mPaint);
    }

    public float[] saveData(float[] data){

        this.data_[0] = data[0];
        this.data_[1] = data[1];
        this.data_[2] = data[2];
        invalidate();
        System.out.println(data_[0]);
        return data;
    }
}