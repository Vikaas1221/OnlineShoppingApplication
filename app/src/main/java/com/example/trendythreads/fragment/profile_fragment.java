package com.example.trendythreads.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.Activity.CreateAccountActivity;
import com.example.trendythreads.Activity.Main2Activity;
import com.example.trendythreads.Adapter.profile_adapter;
import com.example.trendythreads.AllModel.profile_model;
import com.example.trendythreads.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class profile_fragment extends Fragment
{
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    ArrayList<profile_model> modelArrayList;
    TextView signout;
    FirebaseAuth mauth;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=LayoutInflater.from(getContext())
                .inflate(R.layout.profile_layout,container,false);
        recyclerView=view.findViewById(R.id.profile_recycler);
        signout=view.findViewById(R.id.logout);
        progressBar=view.findViewById(R.id.progress);
        Sprite doublebounce=new DoubleBounce();
        progressBar.setIndeterminateDrawable(doublebounce);
        mauth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        modelArrayList=new ArrayList<>();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        Log.d("date",""+thisDate);


        signout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                mauth.signOut();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Intent intent=new Intent(getActivity(), CreateAccountActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        progressBar.setVisibility(View.GONE);
                    }
                },1000);


//                Fragment fragment=new profile_fragment();
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });

        int[] imgArr=
                {
               R.drawable.ic_history_black_24dp,R.drawable.ic_location_on_black_24dp,R.drawable.ic_question_answer_black_24dp,
                        R.drawable.ic_star_black_24dp,
        };
        String[] profile_name={
                "My Orders","My Delivery Address","Faq","Rate Us"
        };

        for (int i=0;i<imgArr.length;i++)
        {
            profile_model obj=new profile_model();
            obj.setImg(imgArr[i]);
            obj.setName(profile_name[i]);
            modelArrayList.add(obj);
        }
        recycler_adapter=new profile_adapter(getContext(),modelArrayList);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

        return view;
    }
}
