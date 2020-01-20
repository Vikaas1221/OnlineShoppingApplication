package com.example.trendythreads.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.AllModel.mnb;
import com.example.trendythreads.R;
import com.example.trendythreads.Activity.shopCategoryActivity;
import com.example.trendythreads.AllModel.shops;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import util.Trendy;

public class shopAdapter extends RecyclerView.Adapter<shopAdapter.ViewHolder>
{
    Context context;
    ArrayList<shops> arrayList;
    Context c1;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference shopref=db.collection("shopActivity");
    public shopAdapter(Context context,ArrayList<shops> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopactivity_layout,parent,false);
        c1=view.getContext();

        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final mnb obj1=new mnb();

       // Log.d("cvb",""+i);
        final shops obj=arrayList.get(position);
        obj1.setI(position);
        holder.shopname.setText(obj.getName());
        Picasso.get().load(obj.getUrl()).into(holder.shopimg);

        holder.shop_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Trendy trendy=Trendy.getInstance();

                String id=obj.getShopid();
                trendy.setShopNum(id);
                Intent intent=new Intent(context, shopCategoryActivity.class);
                intent.putExtra("pos",position);
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
        ImageView shopimg;
        TextView shopname;
        LinearLayout shop_layout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            shopimg=itemView.findViewById(R.id.shopimage);
            shopname=itemView.findViewById(R.id.shopName);
            shop_layout=itemView.findViewById(R.id.shop_linear_layout);

        }
    }
}
