package com.example.trendythreads.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.trendythreads.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    SharedPreferences preferences;
    boolean b1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth=FirebaseAuth.getInstance();
        preferences=getSharedPreferences("shared", Context.MODE_PRIVATE);
        b1=preferences.getBoolean("check",false);

       Log.d("xyz","cv-"+b1);

       Handler handler=new Handler();

        handler.postDelayed(new Runnable()
        {
                @Override
                public void run()
                {
                    Intent intent=new Intent(MainActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);

   }

    }
