package com.example.trendythreads.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.AllModel.profile_model;
import com.example.trendythreads.R;

import java.util.ArrayList;

public class profile_adapter extends RecyclerView.Adapter<profile_adapter.ViewHolder>
{
    Context context;
    ArrayList<profile_model> arrayList_profile;
    public profile_adapter(Context context,ArrayList<profile_model> arrayList_profile)
    {
        this.context=context;
        this.arrayList_profile=arrayList_profile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_fragment_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        final profile_model obj=arrayList_profile.get(position);
        holder.imageView.setImageResource(obj.getImg());
        holder.textView.setText(obj.getName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (position==0)
                {
                    Intent intent=new Intent(context,orderhistory.class);
                    context.startActivity(intent);

                }
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return arrayList_profile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.order_img);
            textView=itemView.findViewById(R.id.list_name);
            relativeLayout=itemView.findViewById(R.id.profile_layout);
        }
    }
}