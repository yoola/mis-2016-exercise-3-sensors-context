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


// Creating a SurfaceView class
// http://android-er.blogspot.de/2014/03/simple-surfaceview-example.html


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder surfaceHolder;
    private int time = 0;
    public float[] data_ = new float[3];
    //private float[] linear_acceleration = new float[3];




    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        //init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        //init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
        //init();
    }

    /*private void init(){
        surfaceHolder = getHolder();

        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                canvas = holder.lockCanvas(null);
                //drawSomething(new float[]{8.0f,7.0f,8.4f});
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }});


    }*/



    public void onDraw(Canvas canvas){

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        float ratio = getHeight()/20;

        //if (time == getWidth()) {
        //    time = 0;
        //}

        for(int i = 0; i <getWidth(); i++){
            paint.setColor(Color.GREEN);
            canvas.drawPoint(getWidth()/2, (float) ((getHeight() / 2) - ratio * data_[0]), paint);
            paint.setColor(Color.RED);
            canvas.drawPoint(getWidth()/2, (float) ((getHeight() / 2) - ratio * data_[1]), paint);
            paint.setColor(Color.BLUE);
            canvas.drawPoint(getWidth()/2, (float) ((getHeight() / 2) - ratio * data_[2]), paint);
            //time++;
        }


        System.out.print("onDraw is called!!!!!! \n");
        System.out.print(data_[0]+"\n");
        System.out.print(data_[1]+"\n");
        System.out.print(data_[2]+"\n");
        canvas.drawPoint(getWidth()/2, getHeight()/2, paint);
        paint.setTextSize(18f);
        canvas.drawText("0", getWidth()-20f, getHeight()/2, paint);
    }

    public float[] saveData(float[] data){

        this.data_ = data;
        System.out.println(data_[0]);
        invalidate();
        return data;
    }






    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setWillNotDraw(false);
        /*Canvas canvas = new Canvas();
        try {
            canvas = surfaceHolder.lockCanvas(null);
            synchronized (surfaceHolder) {
                this.onDraw(canvas);
            }
        } finally {
            surfaceHolder.unlockCanvasAndPost(canvas);


        }*/

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}