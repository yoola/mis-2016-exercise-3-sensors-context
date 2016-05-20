package com.apptwo2.apptwo2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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




public class MainFFT extends AppCompatActivity implements SensorEventListener{

    private TextView mText, mText2;
    private SeekBar mSeekBar;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private MyFFTView mView;

    private final float[] gravity = new float[3];
    private final float[] linear_acceleration = new float[3];
    private double[] X, xInput, yInput; // Input vector size must be a power of 2

    private int mSensorDelay;
    private int N = 16;
    private int counter = 0;

    private double magnitude = 0;
    private double pre_magnitude = 0;

    private String[] action = {"You are not moving right now.", "You are walking right now.", "You are running right now", "You are moving faster then running right now."};
    private String last_action = "";
    private int action_time = 0;
    private int rate = 50;
    private double[] last_magnitudes = new double[rate];


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

        X = new double[N];
        xInput = new double[N];
        yInput = new double[N];



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

        pre_magnitude = Math.sqrt(linear_acceleration[0]*linear_acceleration[0] +
                linear_acceleration[1]*linear_acceleration[1] +
                linear_acceleration[2]*linear_acceleration[2]);


        if(counter < N){

            X[counter] = pre_magnitude;
            counter++;
        }




        if(counter == N){

            magnitude = 0;

            mText2.setText("Magnitude gets computed");

            xInput = X;

            for(int i = 0; i<N; i++){

                yInput[i] = 0.0;
            }



            FFT fft = new FFT(N);
            fft.fft(xInput, yInput);

            for(int j= 0; j< N; j++) {

                magnitude += Math.sqrt((xInput[j] * xInput[j] + yInput[j] * yInput[j]));
            }


            counter = 0;

        }

        mView.saveData(magnitude);
        mText.setText("Magnitude (yellow): "+ magnitude + "\n Counter: " + counter);

        System.out.println(last_magnitudes.length);
        System.out.println(action_time);
        last_magnitudes[action_time] = pre_magnitude; // noch pre magnitude später dann fft transformiterte mag

        if (action_time == rate - 1) {
            String current_action = "";
            double mean = 0.0;
            for (double value : last_magnitudes) {
                mean  += value;
            }
            mean  /= rate;
            if (mean > 6) {
                current_action = action[3];
            } else if (mean > 4.5) {
                current_action = action[2];
            } else if(mean > 2) {
                current_action = action[1];
            } else {
                current_action = action[0];
            }
            System.out.println("MEAN: " + mean);
            newNotification(current_action); // sitting
            Toast.makeText(MainFFT.this, current_action + " " + mean, Toast.LENGTH_SHORT).show();

            action_time = 0;
        }
        action_time++;

    }

    public void newNotification(String current_action) {
        if (current_action != last_action) {
            // http://www.vogella.com/tutorials/AndroidNotifications/article.html
            // Prepare intent which is triggered if the notification is selected
            Intent intent = new Intent(this, NotificationReceiverActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

            // Build notification
            // Actions are just fake
            Notification noti = new Notification.Builder(this)
                    .setContentTitle(current_action)
                    .setContentText("Subject").setSmallIcon(R.drawable.icon)
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.drawable.icon)
                    .addAction(R.drawable.icon, "See information.", pIntent).build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);
            last_action = current_action;
        }
    }
}


