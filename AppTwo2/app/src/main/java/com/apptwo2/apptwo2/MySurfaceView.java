package com.apptwo2.apptwo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;




public class MySurfaceView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;


    public MySurfaceView() {
        super(null);
        init();
    }

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        surfaceHolder = getHolder();

        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                canvas = holder.lockCanvas(null);
                drawSomething(new float[]{1f, 0.5f, 0.7f});
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
    }

    protected void drawSomething(float[] linear_acceleration) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.GREEN);
        float ratio = getHeight()/20;

        for(int i = 0; i<getWidth(); i++){

            canvas.drawPoint((float)i, (float)((getHeight()/2) - ratio * 2.56), paint);
        }

        canvas.drawPoint(getWidth()/2, getHeight()/2, paint);
        paint.setTextSize(18f);
        canvas.drawText("0", getWidth()-20f, getHeight()/2, paint);

    }

}