package com.example.trendythreads.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trendythreads.Adapter.adapter;
import com.example.trendythreads.AllModel.slidermodel;
import com.example.trendythreads.R;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import util.Trendy;

public class CreateAccountActivity extends AppCompatActivity  {
    EditText Username,MobileNumber,Otp;
    TextView generateOtp;
    Button createAccountButton;
    FirebaseAuth mauth;
    String codeSent;
    String[] a1={"Daal","White chana","Rajama"};
    int[] a2={
            R.drawable.pulses,R.drawable.chanapulse,R.drawable.kidneybeans
    };
    ArrayList<slidermodel> slider_arrayList;

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


        SliderView view=findViewById(R.id.imageSlider);
        slider_arrayList=new ArrayList<>();
        for (int i=0;i<a1.length;i++)
        {
            slidermodel model=new slidermodel();
            model.setImg(a2[i]);
            model.setText(a1[i]);
            slider_arrayList.add(model);
        }
        com.example.trendythreads.Adapter.adapter as=new adapter(this,slider_arrayList);
        view.setSliderAdapter(as);
        view.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        view.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        view.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        view.setIndicatorSelectedColor(Color.WHITE);
        view.setIndicatorUnselectedColor(Color.GRAY);
        view.setScrollTimeInSec(2); //set scroll delay in seconds :
        view.startAutoCycle();

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
