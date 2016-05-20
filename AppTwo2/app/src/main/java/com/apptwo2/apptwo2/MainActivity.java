package com.apptwo2.apptwo2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;




public class MainActivity extends AppCompatActivity{

    Button mButtonAcc;
    Button mButtonFFT;
    Button mButtonNot;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonAcc = (Button) findViewById(R.id.button_acc);
        mButtonFFT = (Button) findViewById(R.id.button_fft);
        mButtonNot = (Button) findViewById(R.id.button_not);

    }

    public void GoToAcc(View view){

        Intent intent = new Intent(this, MainAccelerometer.class);
        startActivity(intent);

    }

    public void GoToFFT(View view){

        Intent intent = new Intent(this, MainFFT.class);
        startActivity(intent);
    }

    public void createNotification(View view) {
        newNotification(view);
    }

    public void newNotification(View view) {
        // http://www.vogella.com/tutorials/AndroidNotifications/article.html
        // Prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Action")
                .setContentText("Movement will be calculated: Click FFT").setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .addAction(R.drawable.icon, "Call", pIntent)
                .addAction(R.drawable.icon, "More", pIntent)
                .addAction(R.drawable.icon, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }

}
