package com.example.trendythreads.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trendythreads.R;
import com.google.firebase.auth.FirebaseAuth;

import util.Trendy;

public class CreateAccountActivity extends AppCompatActivity  {
    EditText Username,MobileNumber,Otp;
    TextView generateOtp;
    Button createAccountButton;
    FirebaseAuth mauth;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        MobileNumber=findViewById(R.id.mobileNumber);
        createAccountButton=findViewById(R.id.createAccount);
        mauth=FirebaseAuth.getInstance();
        createAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                generateOtpFunction();
            }
        });

    }
    public void generateOtpFunction()
    {
        String mobileNumber=MobileNumber.getText().toString();
        if (mobileNumber.isEmpty()&&mobileNumber.length()<10)
        {
            MobileNumber.setError("check your mobile number");
            MobileNumber.requestFocus();
            return;
        }
        Intent intent=new Intent(CreateAccountActivity.this, OtpVerificationActivity.class);
        Trendy trendy=Trendy.getInstance();
        trendy.setMobileNo(mobileNumber);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mauth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(CreateAccountActivity.this, Main2Activity.class);
            startActivity(intent);
            finish();
        }

    }
}
