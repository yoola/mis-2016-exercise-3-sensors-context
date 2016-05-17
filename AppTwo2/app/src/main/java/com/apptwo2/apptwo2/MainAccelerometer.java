package com.apptwo2.apptwo2;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;





// Creating a SurfaceView class
// http://android-er.blogspot.de/2014/03/simple-surfaceview-example.html


public class MainAccelerometer extends AppCompatActivity implements SensorEventListener{

    //private static final String LOG_TAG = MainAccelerometer.class.getSimpleName();

    private TextView xText;
    private SeekBar mSeekBar;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private final float[] gravity = new float[3];
    private final float[] linear_acceleration = new float[3];
    private final float[] data = new float[3];
    private int mSensorDelay;
    private MySurfaceView view;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview_acc);
        view = (MySurfaceView) findViewById(R.id.surfaceView);



        // Get an instance to the accelerometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorDelay = SensorManager.SENSOR_DELAY_NORMAL;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, mSensorDelay);


        xText = (TextView)findViewById(R.id.xText);

        // Setting the seekbar
        //http://stackoverflow.com/questions/8956218/android-seekbar-setonseekbarchangelistener

        mSeekBar = (SeekBar)findViewById(R.id.seekBar);
        mSeekBar.setProgress(0);
        mSeekBar.setMax(3);



        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress){
                    case 0:

                        //suitable for monitoring typical screen orientation changes and uses a delay of 200,000 microseconds
                        Toast.makeText(MainAccelerometer.this, "SENSOR_DELAY_NORMAL : 200.000 ms delay", Toast.LENGTH_SHORT).show();
                        mSensorDelay = SensorManager.SENSOR_DELAY_NORMAL;
                        break;
                    case 1:
                        // 0 microsecond delay
                        Toast.makeText(MainAccelerometer.this, "SENSOR_DELAY_FASTEST : 0 ms delay", Toast.LENGTH_SHORT).show();
                        mSensorDelay = SensorManager.SENSOR_DELAY_FASTEST;
                        break;
                    case 2:
                        // 20,000 microsecond delay
                        Toast.makeText(MainAccelerometer.this, "SENSOR_DELAY_GAME : 20.000 ms delay", Toast.LENGTH_SHORT).show();
                        mSensorDelay = SensorManager.SENSOR_DELAY_GAME;
                        break;
                    case 3:
                        // 60,000 microsecond delay
                        Toast.makeText(MainAccelerometer.this, "Set: SENSOR_DELAY_UI : 60.000 ms delay", Toast.LENGTH_SHORT).show();
                        mSensorDelay = SensorManager.SENSOR_DELAY_UI;
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        // not in use

    }

    // always called whenever the device is in motion
    public void onSensorChanged(SensorEvent event) {


        // https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-gyro

        final float alpha = 0.8f;

        float acceleration= (float)Math.sqrt(gravity[0]*gravity[0] +
                gravity[1]*gravity[1] +
                gravity[2]*gravity[2]);


        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        data[0] = event.values[0];
        data[1] = event.values[1];
        data[2] = event.values[2];


        xText.setText(" X: " + linear_acceleration[0] + "\n Y: " + linear_acceleration[1] +
                "\n Z: " + linear_acceleration[2] + "\n Magnitude: "+ acceleration);


        view.saveData(data);
        view.invalidate();
        //Canvas canvas = new Canvas();
        //MySurfaceView view = (MySurfaceView)findViewById(R.id.surfaceView);
        //view.drawSomething(linear_acceleration);

    }
}


