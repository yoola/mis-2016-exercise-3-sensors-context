package com.apptwo2.apptwo2;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;




public class MainActivity extends AppCompatActivity{

    Button mButtonAcc;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonAcc = (Button) findViewById(R.id.button_acc);


    }

    public void GoToAcc(View view){

        Intent intent = new Intent(this, MainAccelerometer.class);
        startActivity(intent);
    }

}
