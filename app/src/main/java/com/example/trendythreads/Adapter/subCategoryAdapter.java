package com.example.trendythreads.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.AllModel.mnb;
import com.example.trendythreads.R;
import com.example.trendythreads.AllModel.model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import util.Trendy;

//import static com.example.trendythreads.Activity.testingActivity.cartshopid;
import static com.example.trendythreads.Activity.testingActivity.cartid;
import static com.example.trendythreads.Activity.testingActivity.ds;
import static com.example.trendythreads.Activity.testingActivity.paynow;
import static com.example.trendythreads.Activity.testingActivity.reference1;
import static com.example.trendythreads.fragment.cart_fragment.currentUserid;

public class subCategoryAdapter extends RecyclerView.Adapter<subCategoryAdapter.ViewHolder>
{
    String cartshopid;
    Context context;
    int i,j=0;
    String docid;
    String shopi;
    int qu;
    String currentquantity,currentquantity2;
    ArrayList<model> arrayList;
    FirebaseAuth mauth;
    FirebaseUser user;
    String userid;
    String id,v;
    Context mcontext;
    String e1Quantity;
    String imgurl;
    String itemname,itemprice;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("CartOrder");
    DocumentReference ref;
    CollectionReference shopref=db.collection("shopActivity");

    public subCategoryAdapter(Context context, ArrayList<model> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcategory_layout,parent,false);

        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        userid=user.getUid();
        mcontext=parent.getContext();
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {





  //      final String trendyShopid= trendy.getShopNum();
        final model obj=arrayList.get(position);
        int s=arrayList.size();
        Log.d("sad",""+s);
        holder.itemname.setText(obj.getItemName());
        holder.itemprice.setText("Rs "+obj.getItemprice()+"perKg");
        Picasso.get().load(obj.getItemImage()).into(holder.itemImg);
        final String str=obj.getItemName();
      //  String price=obj.getItemprice();


        reference.whereEqualTo("userid",userid).whereEqualTo("itemname",str)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        assert queryDocumentSnapshots != null;
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            String q=documentSnapshot.getString("Quantity");
                            holder.quantity.setText(q);
                        }
                    }
                });






        reference.whereEqualTo("userid",userid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            cartid=documentSnapshot.getString("shopid");
                        }
                    }
                });






        reference.whereEqualTo("userid",userid)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                    {
                        assert queryDocumentSnapshots != null;
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                       {
                           String sid=documentSnapshot.getString("shopid");
                           String fds=documentSnapshot.getString("itemname");
                           assert fds != null;
                           if (fds.equals(str)&&sid.equals(ds))
                           {
                               holder.operation.setVisibility(View.VISIBLE);
                             //  holder.removeItem.setVisibility(View.VISIBLE);
                               holder.addtolist.setVisibility(View.GONE);

                           }
                       }
                    }
                });


        holder.addtolist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String quantity="1";
                holder.quantity.setText(quantity);





                final String shopId=obj.getShopid();
                Log.d("shopidfg",""+cartid);

                Log.d("trendyshop",""+ds);
            //    Log.d("trendyshopid",""+ds+"/shoi"+shopi);
                if (cartid==null||cartid.equals(ds))
                {

                    imgurl = obj.getItemImage();
                    itemname = obj.getItemName();
                    itemprice = obj.getItemprice();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//                    View view = LayoutInflater.from(mcontext)
//                            .inflate(R.layout.addtocart_dialog_layout, null);
//                    final EditText e1 = view.findViewById(R.id.quantity);
//                    final Button sublit = view.findViewById(R.id.submit);
//                    final AlertDialog dialog = builder.create();
//                    dialog.setView(view);
//                    dialog.show();

//                    sublit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            e1Quantity = e1.getText().toString().trim();
//                            if (e1Quantity.isEmpty()) {
//                                e1.setError("Enter Quantity");
//                                e1.requestFocus();
//                                return;
//                            }
                            Map<String, Object> map = new HashMap<>();
                            map.put("imgUrl", imgurl);
                            map.put("itemname", itemname);
                            map.put("itemprice", itemprice);
                            map.put("userid", userid);
                            map.put("Quantity", quantity);
                            map.put("shopid", shopId);
                            reference.add(map)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(context, "Sucessfully added to cart", Toast.LENGTH_SHORT).show();
                                            Log.d("document", "" + documentReference.getId());
                                            id = documentReference.getId();
                                            holder.addtolist.setVisibility(View.GONE);
                                            holder.operation.setVisibility(View.VISIBLE);
                                            holder.quantity.setText(quantity);
                                           // i++;

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                            holder.addtolist.setVisibility(View.VISIBLE);
                                        }
                                    });
                          //  dialog.dismiss();


                        }

         //           });
                else
                {
                    Toast.makeText(context,"You are not allowed to select from different shop",Toast.LENGTH_SHORT).show();
                }





            }
        });

        holder.incrementQ.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String a=obj.getItemName();
                reference.whereEqualTo("userid",userid).whereEqualTo("itemname",a)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                        {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {
                                if (!queryDocumentSnapshots.isEmpty())
                                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                                {
                                    String p_quantity=documentSnapshot.getString("Quantity");
                                    int i_quantity= Integer.parseInt(p_quantity);
                                    i_quantity=i_quantity+1;
                                    String s_quantity=String.valueOf(i_quantity);
                                   docid= documentSnapshot.getId();

                                   ref=reference.document(docid);
                                    final int finalI_quantity = i_quantity;
                                    ref.update("Quantity",s_quantity)
                                           .addOnCompleteListener(new OnCompleteListener<Void>()
                                           {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task)
                                               {
                                                   if (task.isSuccessful())
                                                   {
                                                     //  holder.quantity.setText(finalI_quantity);
                                                       Toast.makeText(context,"updated sucessgully",Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                           });


                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });


        holder.decrementQ.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String qwe=holder.quantity.getText().toString();
                final String a=obj.getItemName();
                reference.whereEqualTo("userid",userid).whereEqualTo("itemname",a)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                        {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {
                                if (!queryDocumentSnapshots.isEmpty())
                                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                                    {
                                        String p_quantity=documentSnapshot.getString("Quantity");
                                        int i_quantity= Integer.parseInt(p_quantity);
                                            i_quantity = i_quantity - 1;
                                            if (i_quantity>0)
                                            {
                                            String s_quantity = String.valueOf(i_quantity);
                                            docid = documentSnapshot.getId();

                                            ref = reference.document(docid);
                                            final int finalI_quantity = i_quantity;
                                            ref.update("Quantity", s_quantity)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                //  holder.quantity.setText(finalI_quantity);
                                                                Toast.makeText(context, "updated sucessgully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        else
                                        {
                                            holder.addtolist.setVisibility(View.VISIBLE);
                                            holder.operation.setVisibility(View.GONE);

                                            reference.whereEqualTo("itemname", a).whereEqualTo("userid", userid)
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                                id = documentSnapshot.getId();
                                                                DocumentReference documentReference;
                                                                documentReference = reference.document(id);
                                                                documentReference.delete()
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
                                          //  holder.operation.setVisibility(View.INVISIBLE);
                                          //  holder.quantity.setText(quantity);
                                        }


                                    }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
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
        TextView itemname,quantity;
        ImageView itemImg,incrementQ,decrementQ;
        TextView itemprice;
        TextView addtolist;
        ImageView increment,decrement,removeItem;
        RelativeLayout operation;

        public ViewHolder(@NonNull final View itemView)
        {
            super(itemView);
            itemname=itemView.findViewById(R.id.itemName);
            itemImg=itemView.findViewById(R.id.itemimage);
            itemprice=itemView.findViewById(R.id.price);
            addtolist=itemView.findViewById(R.id.addtolist);
            increment=itemView.findViewById(R.id.increment);
            operation=itemView.findViewById(R.id.operation);
            removeItem=itemView.findViewById(R.id.remove);
            incrementQ=itemView.findViewById(R.id.increment);
            decrementQ=itemView.findViewById(R.id.decrement);
            quantity=itemView.findViewById(R.id.pricecart);

        }
    }
}
