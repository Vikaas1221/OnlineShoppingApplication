package com.example.trendythreads.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.R;
import com.example.trendythreads.AllModel.cartModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.trendythreads.fragment.cart_fragment.Total;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder>
{
    Context context;
    int sum=0;
    ArrayList<cartModel> arrayList;
    public cartAdapter(Context context,ArrayList<cartModel> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        cartModel model=arrayList.get(position);
        holder.item_name.setText(model.getItemname());
        holder.item_quantity.setText(model.getItemquantity());
        holder.item_price.setText(model.getItemprice());
        Picasso.get().load(model.getImgUrl()).into(holder.item_img);
        String price=model.getItemprice();
        String quantity=model.getItemquantity();
        int i_price=Integer.parseInt(price);
        int i_quantity=Integer.parseInt(quantity);
        int i_total=i_price*i_quantity;
        sum=sum+i_total;
        Total.setText("Rs "+sum);
       // Log.d("as",""+sum);

    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView item_img;
        TextView item_name,item_price,item_quantity;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            item_img=itemView.findViewById(R.id.itemimage);
            item_name=itemView.findViewById(R.id.itemnamecart);
            item_price=itemView.findViewById(R.id.priceITem);
            item_quantity=itemView.findViewById(R.id.pricecart);
        }
    }
}
