package com.example.trendythreads.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.Activity.testingActivity;
import com.example.trendythreads.R;
import com.example.trendythreads.AllModel.shopcatgory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class shopCategoryAdapter extends RecyclerView.Adapter<shopCategoryAdapter.ViewHolder>
{
    Context context;
    ArrayList<shopcatgory> arrayList;
    public shopCategoryAdapter(Context context, ArrayList<shopcatgory> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopcategory_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        final shopcatgory obj=arrayList.get(position);
        holder.itemname.setText(obj.getItemName());
        Picasso.get().load(obj.getItemImage()).into(holder.itemImg);
        holder.itemname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, testingActivity.class);
                intent.putExtra("pos",position);
                intent.putExtra("name",obj.getItemName());
                Log.d("sdf",""+obj.getItemName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemname;
        ImageView itemImg;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemname=itemView.findViewById(R.id.itemName);
            itemImg=itemView.findViewById(R.id.itemimage);
        }
    }
}
