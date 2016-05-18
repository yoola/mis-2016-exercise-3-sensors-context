package com.apptwo2.apptwo2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;




public class MainFFT extends AppCompatActivity implements SensorEventListener{

    private TextView mText, mText2;
    private SeekBar mSeekBar;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private final float[] gravity = new float[3];
    private final float[] linear_acceleration = new float[3];
    private int mSensorDelay;
    private MyFFTView mView;
    private int N = 64;
    private double[] x, xInput, yInput; // Input vector size mus be a power of 2
    private int counter = 0;
    private double magnitude = 0;
    private double pre_magnitude = 0;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_fft);

        mView = (MyFFTView) findViewById(R.id.viewF);
        mText = (TextView)findViewById(R.id.textF);
        mText2 = (TextView)findViewById(R.id.textF2);



        // Get an instance to the accelerometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorDelay = SensorManager.SENSOR_DELAY_NORMAL;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, mSensorDelay);


        // Setting the seekbar
        //http://stackoverflow.com/questions/8956218/android-seekbar-setonseekbarchangelistener

        mSeekBar = (SeekBar)findViewById(R.id.seekBarF);
        mSeekBar.setProgress(0);
        mSeekBar.setMax(3);

        x = new double[N];
        xInput = new double[N];
        yInput = new double[N];

        /*if(counter == N){

            xInput = x;

            for(int i = 0; i<=N; i++){

                yInput[i] = 0;
            }

            FFT fft = new FFT(N);
            fft.fft(xInput, yInput);

            for(int j= 0; j<= N; j++){

                magnitude += Math.sqrt(xInput[j]*xInput[j]+yInput[j]*yInput[j]);
            }
        }*/





        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress){
                    case 0:

                        //suitable for monitoring typical screen orientation changes and uses a delay of 200,000 microseconds
                        Toast.makeText(MainFFT.this, "SENSOR_DELAY_NORMAL : 200.000 ms delay", Toast.LENGTH_SHORT).show();
                        mSensorDelay = SensorManager.SENSOR_DELAY_NORMAL;
                        break;
                    case 1:
                        // 0 microsecond delay
                        Toast.makeText(MainFFT.this, "SENSOR_DELAY_FASTEST : 0 ms delay", Toast.LENGTH_SHORT).show();
                        mSensorDelay = SensorManager.SENSOR_DELAY_FASTEST;
                        break;
                    case 2:
                        // 20,000 microsecond delay
                        Toast.makeText(MainFFT.this, "SENSOR_DELAY_GAME : 20.000 ms delay", Toast.LENGTH_SHORT).show();
                        mSensorDelay = SensorManager.SENSOR_DELAY_GAME;
                        break;
                    case 3:
                        // 60,000 microsecond delay
                        Toast.makeText(MainFFT.this, "Set: SENSOR_DELAY_UI : 60.000 ms delay", Toast.LENGTH_SHORT).show();
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


        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        // https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-gyro

        pre_magnitude = (double)Math.sqrt(gravity[0]*gravity[0] +
                gravity[1]*gravity[1] +
                gravity[2]*gravity[2]);


        if(counter < N){

            x[counter] = pre_magnitude;
            counter++;
        }else{

            counter = 0;
        }




        mText.setText("Magnitude (yellow): "+ magnitude + "\n Counter: " + counter);
        mView.saveData(pre_magnitude);
    }
}


