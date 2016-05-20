package com.apptwo2.apptwo2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;



public class MySurfaceView extends View {

    private int time_new = 0;
    private int time_old = 0;
    private int delay = 0;
    private int current_delay = 0;
    private float[] data_ = new float[3];
    private float magnitude_;
    private Paint mPaint;
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();
    private Path path4 = new Path();
    private float x,y,z,m;

    public MySurfaceView(Context context, AttributeSet attrs) {
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
            path2 = new Path();
            path3 = new Path();
            path4 = new Path();

        }

        time_old = time_new;
        time_new+= 3;

        x = ((getHeight() / 2) - ratio * data_[0]);
        y = ((getHeight() / 2) - ratio * data_[1]);
        z = ((getHeight() / 2) - ratio * data_[2]);
        m = ((getHeight() / 2) - ratio * magnitude_);

        mPaint.setColor(Color.GREEN);
        path1.lineTo(time_old, x);
        canvas.drawPath(path1, mPaint);

        mPaint.setColor(Color.RED);
        path2.lineTo(time_old, y);
        canvas.drawPath(path2, mPaint);

        mPaint.setColor(Color.BLUE);
        path3.lineTo(time_old, z);
        canvas.drawPath(path3, mPaint);

        mPaint.setColor(Color.WHITE);
        path4.lineTo(time_old, m);
        canvas.drawPath(path4, mPaint);

        mPaint.setTextSize(18f);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("10 - ", getWidth()-50f, getHeight()/4, mPaint);
        canvas.drawText("0 - ", getWidth()-50f, getHeight()/2, mPaint);
        canvas.drawText("-10 - ", getWidth()-50f, 3*(getHeight()/4), mPaint);
    }

    public void saveData(float[] data, float magnitude, int del) {
        if (current_delay < delay) {
            invalidate();
            current_delay++;
        } else {
            delay = del;
            this.data_[0] = data[0];
            this.data_[1] = data[1];
            this.data_[2] = data[2];
            this.magnitude_ = magnitude;
            System.out.println(data_[0]);
            invalidate();
            current_delay = 0;
        }
    }

}