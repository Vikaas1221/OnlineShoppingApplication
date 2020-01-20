package com.example.trendythreads.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trendythreads.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import util.Trendy;

public class OtpVerificationActivity extends AppCompatActivity {
    EditText verificationCode;
    Button submitButton;
    TextView resendOtp;
    String codeSent;
    String userName;
    FirebaseAuth mauth;
    FirebaseUser currentUser;
    String mobile;
    ProgressBar progressBar;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference collectionReference=db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        progressBar=findViewById(R.id.progress);
        Sprite doublebounce=new DoubleBounce();
        progressBar.setIndeterminateDrawable(doublebounce);
        verificationCode=findViewById(R.id.verificationCode);
        submitButton=findViewById(R.id.submitOtp);
       // resendOtp=findViewById(R.id.ResendOtp);
        mauth=FirebaseAuth.getInstance();

         mobile= Trendy.getInstance().getMobileNo();

        senderificationCode(mobile);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
              String code=verificationCode.getText().toString().trim();
              if (code.isEmpty()||code.length()<6)
              {
                  verificationCode.setError("Invalid code");
                  verificationCode.requestFocus();
                  progressBar.setVisibility(View.INVISIBLE);
                  return;
              }
              verifyVerificationCode(code);
              progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void senderificationCode(String mobile)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+mobile,60, TimeUnit.SECONDS,this,mCallbacks);
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
        {
            String code=phoneAuthCredential.getSmsCode();
            if (code!=null)
            {

                verificationCode.setText(code);
                progressBar.setVisibility(View.VISIBLE);
                verifyVerificationCode(code);
//                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
        }
    };
    public void verifyVerificationCode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeSent,code);
        signInWithCredential(credential);
    }
    public void signInWithCredential(PhoneAuthCredential credential)
    {
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            currentUser=mauth.getCurrentUser();
                            final String currentUserId=currentUser.getUid();

                            Map<String,String> map=new HashMap<>() ;
                        //    map.put("username",userName);
                            map.put("CurrentUserId",currentUserId);
                            map.put("mobile",mobile);
                            collectionReference.add(map)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                    {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference)
                                        {
                                            Trendy trendy=Trendy.getInstance();
                                            trendy.setUserdId(currentUserId);
                                           trendy.setMobileNo(mobile);
                                           Intent intent=new Intent(OtpVerificationActivity.this, Main2Activity.class);
                                           startActivity(intent);

                                           Toast.makeText(getApplicationContext(),"Sucessfully created and firestore",Toast.LENGTH_SHORT).show();


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
