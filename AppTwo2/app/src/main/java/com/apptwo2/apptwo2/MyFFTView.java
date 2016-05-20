package com.apptwo2.apptwo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;



public class MyFFTView extends View {

    private int time_new = 0;
    private int time_old = 0;
    private double magnitude_;
    private Paint mPaint;
    private Path path1 = new Path();
    private double m;



    public MyFFTView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
    }

    @Override
    public void onDraw(Canvas canvas){

        System.out.print("Width: "+getWidth()+"\n");
        System.out.print("Height: "+getHeight()+ "\n");


        float ratio = getHeight()/300;




        if (time_new >= getWidth()) {
            time_new = 0;

            path1 = new Path();


        }

        time_old = time_new;
        time_new+= 5;

        m = (getHeight() - ratio * magnitude_);
        System.out.print("m: "+m);


        mPaint.setColor(Color.WHITE);
        path1.lineTo(time_old, (float)m);
        canvas.drawPath(path1, mPaint);

    }

    public void saveData(double magnitude){

        this.magnitude_ = magnitude;


        invalidate();
    }
}