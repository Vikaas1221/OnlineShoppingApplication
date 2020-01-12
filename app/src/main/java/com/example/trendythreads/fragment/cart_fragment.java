package com.example.trendythreads.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.R;
import com.example.trendythreads.Adapter.cartAdapter;
import com.example.trendythreads.AllModel.cartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import util.Trendy;

public class cart_fragment extends Fragment
{
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter adapter;
    ArrayList<cartModel> arrayList;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth mauth;
    FirebaseUser user;
    CollectionReference reference=db.collection("CartOrder");
    ImageView emptyimg;
    Button checkoutbutton;
    public static TextView Total;
    int sum=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.cart_fragment,container,false);
        mauth=FirebaseAuth.getInstance();
        recyclerView=view.findViewById(R.id.cartRecycler);
        relativeLayout=view.findViewById(R.id.totalbilllayout);
        emptyimg=view.findViewById(R.id.emptylist);
        Total=view.findViewById(R.id.totalbill);
        checkoutbutton=view.findViewById(R.id.checkout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        user=mauth.getCurrentUser();
       // String shopid= Trendy.getInstance().getShopNum();

        String currentUserid=user.getUid();

        reference.whereEqualTo("userid",currentUserid)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
                    {
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            cartModel model=new cartModel();
                            model.setImgUrl(documentSnapshot.getString("imgUrl"));
                            model.setItemname(documentSnapshot.getString("itemname"));
                            model.setItemprice(documentSnapshot.getString("itemprice"));
                            model.setItemquantity(documentSnapshot.getString("Quantity"));
                            arrayList.add(model);

                        }
                        int s=arrayList.size();
                        if (s==0)
                        {
                            relativeLayout.setVisibility(View.INVISIBLE);
                            emptyimg.setVisibility(View.VISIBLE);

                        }
                        else if (s>0)
                        {
                            relativeLayout.setVisibility(View.VISIBLE);
                            emptyimg.setVisibility(View.INVISIBLE);
                            cartModel obj=new cartModel();

                        }
                        adapter=new cartAdapter(getContext(),arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
        return view;

    }
}
