package com.example.trendythreads.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.AllModel.cartupdatemodel;
import com.example.trendythreads.R;
import com.example.trendythreads.AllModel.cartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.annotation.Nullable;

//import static com.example.trendythreads.Activity.testingActivity.placeorder;
import static com.example.trendythreads.fragment.cart_fragment.Total;
import static com.example.trendythreads.fragment.cart_fragment.currentUserid;
import static com.example.trendythreads.fragment.cart_fragment.sta;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder>
{
    String docid;
    Context context;
    int sum=0;
    ArrayList<cartModel> arrayList;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference collectionReference=db.collection("CartOrder");
    DocumentReference reference;
    FieldValue fieldValue;
    String quantity,id;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {

        final cartModel model=arrayList.get(position);
        holder.item_name.setText(model.getItemname());
        holder.item_quantity.setText(model.getItemquantity());
        holder.item_price.setText(model.getItemprice());
        Picasso.get().load(model.getImgUrl()).into(holder.item_img);
        String price=model.getItemprice();
        final String[] quantity = {model.getItemquantity()};
        int i_price=Integer.parseInt(price);
        int i_quantity=Integer.parseInt(quantity[0]);
        int i_total=i_price*i_quantity;
        sum=sum+i_total;
       // Total.setText("Rs "+sum);
        sta.setText("Rs: "+sum);

        holder.incrementQuantity.setOnClickListener(new View.OnClickListener()
        {

            String item=model.getItemname();
            String quantity=model.getItemquantity();

            @Override
            public void onClick(View v)
            {


                int i=Integer.parseInt(quantity);
                i=i+1;
                final String newQuantity=String.valueOf(i);
                collectionReference.whereEqualTo("itemname",item).whereEqualTo("userid",currentUserid)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                        {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {
                                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                                {
                                    id=documentSnapshot.getId();
                                    reference=collectionReference.document(id);
                                    reference.update("Quantity",newQuantity)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(context,"updated sucessfully",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(context,"not updated sucessfully",Toast.LENGTH_SHORT).show();
                                                    }
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
                                Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
        holder.decrementQuantity.setOnClickListener(new View.OnClickListener()
        {
            String item=model.getItemname();
            String quantity=model.getItemquantity();
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(quantity);
                i = i - 1;
                if (i> 0) {

                    final String newQuantity = String.valueOf(i);
                    collectionReference.whereEqualTo("itemname", item).whereEqualTo("userid", currentUserid)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        id = documentSnapshot.getId();
                                        reference = collectionReference.document(id);
                                        reference.update("Quantity", newQuantity)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(context, "updated sucessfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(context, "not updated sucessfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else
                    {

                    collectionReference.whereEqualTo("itemname", item).whereEqualTo("userid", currentUserid)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        id = documentSnapshot.getId();
                                        reference = collectionReference.document(id);
                                        reference.delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(context, "removed sucessfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });

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
        ImageView incrementQuantity,decrementQuantity;
        TextView item_name,item_price,item_quantity;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            item_img=itemView.findViewById(R.id.itemimage);
            item_name=itemView.findViewById(R.id.itemnamecart);
            item_price=itemView.findViewById(R.id.priceITem);
            item_quantity=itemView.findViewById(R.id.pricecart);
            incrementQuantity=itemView.findViewById(R.id.increment);
            decrementQuantity=itemView.findViewById(R.id.decrement);
        }
    }
}
