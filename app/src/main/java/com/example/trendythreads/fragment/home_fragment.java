package com.example.trendythreads.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendythreads.R;
import com.example.trendythreads.Adapter.shopAdapter;
import com.example.trendythreads.AllModel.shops;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class home_fragment extends Fragment
{

    FirebaseAuth mauth;
    FirebaseUser user;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("Users");
    CollectionReference referenceshop=db.collection("shopActivity");
    RecyclerView recyclerView;
    ArrayList<shops> arrayList;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.activity_dashbord,container,false);
        progressBar=view.findViewById(R.id.progress);
        Sprite doublebounce=new DoubleBounce();
        progressBar.setIndeterminateDrawable(doublebounce);
        recyclerView=view.findViewById(R.id.shopRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        final String currentId=user.getUid();
        Trendy trendy=Trendy.getInstance();
        trendy.setUserdId(currentId);
        reference.whereEqualTo("CurrentUserId",currentId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
                    {
                        assert queryDocumentSnapshots != null;
                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                            {
                                String mobileno=documentSnapshot.getString("mobile");
                                Log.d("Mobile",mobileno);
                                Log.d("USerid",currentId);
                            }
                        }
                    }
                });
        referenceshop.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            for (QueryDocumentSnapshot queryDocumentSnapshots1:queryDocumentSnapshots)
                            {
                                String imgurl=queryDocumentSnapshots1.getString("imgUrl");
                                String name=queryDocumentSnapshots1.getString("name");
                                String shopid=queryDocumentSnapshots1.getString("shopid");
                                Log.d("url",""+imgurl);
                                Log.d("shopid",""+shopid);
                                shops obj=new shops();
                                obj.setUrl(imgurl);
                                obj.setName(name);
                                obj.setShopid(shopid);
                                arrayList.add(obj);
                            }
                            adapter=new shopAdapter(getContext(),arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
        return view;


    }
}
