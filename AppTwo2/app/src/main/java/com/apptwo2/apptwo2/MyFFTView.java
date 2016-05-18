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
        float ratio = getHeight()/40;


        if (time_new == getWidth()) {
            time_new = 0;

            path1 = new Path();


        }

        time_old = time_new;
        time_new++;

        m = ((getHeight() / 2) - ratio * magnitude_);


        mPaint.setColor(Color.GREEN);
        path1.lineTo(time_old, (float)m);
        canvas.drawPath(path1, mPaint);



        mPaint.setTextSize(18f);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("10 - ", getWidth()-50f, getHeight()/4, mPaint);
        canvas.drawText("0 - ", getWidth()-50f, getHeight()/2, mPaint);
        canvas.drawText("-10 - ", getWidth()-50f, 3*(getHeight()/4), mPaint);
    }

    public void saveData(double magnitude){

        this.magnitude_ = magnitude;

        invalidate();
    }
}