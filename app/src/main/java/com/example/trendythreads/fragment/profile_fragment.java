package com.example.trendythreads.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.Adapter.profile_adapter;
import com.example.trendythreads.AllModel.profile_model;
import com.example.trendythreads.R;

import java.util.ArrayList;

public class profile_fragment extends Fragment
{
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    ArrayList<profile_model> modelArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=LayoutInflater.from(getContext())
                .inflate(R.layout.profile_layout,container,false);
        recyclerView=view.findViewById(R.id.profile_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        modelArrayList=new ArrayList<>();

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
