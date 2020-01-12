package com.example.trendythreads.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.trendythreads.AllModel.mnb;
import com.example.trendythreads.R;
import com.example.trendythreads.fragment.cart_fragment;
import com.example.trendythreads.AllModel.model;
import com.example.trendythreads.Adapter.subCategoryAdapter;
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

import javax.annotation.Nullable;

import util.Trendy;

public class testingActivity extends AppCompatActivity
{
   public static String ds;
    ActionBar toolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<model> arrayList;
    RelativeLayout layout;
  public static   Button placeorder;
    FirebaseAuth mauth;
    String currentid;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
   CollectionReference reference2=db.collection("CartOrder");
   public static CollectionReference reference1;
    ProgressBar progressBar;
    public static String cartid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        progressBar=findViewById(R.id.progress);
        Sprite doublebounce=new DoubleBounce();
        progressBar.setIndeterminateDrawable(doublebounce);
        toolbar=getSupportActionBar();
        mauth= FirebaseAuth.getInstance();
        placeorder=findViewById(R.id.placeorder);
        layout=findViewById(R.id.ghjf);
        FirebaseUser user=mauth.getCurrentUser();
       currentid=user.getUid();
        Log.d("curret",currentid);
        Bundle b=getIntent().getExtras();
        int postion=b.getInt("pos");
        postion=postion+1;
        String name=b.getString("name");
         ds=Trendy.getInstance().getShopNum();
        Log.d("shopnum",""+ds);
        reference1=db.collection("shopActivity").document(ds).collection("items").document("subItem"+postion).collection(name);
        recyclerView=findViewById(R.id.shopCatgoryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);

      //  arrayList.clear();
        reference1.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            model obj=new model();
                            obj.setItemName(documentSnapshot.getString("itemname"));
                            obj.setItemImage(documentSnapshot.getString("imgUrl"));
                            obj.setItemprice(documentSnapshot.getString("price"));
                            obj.setShopid(documentSnapshot.getString("shopid"));

                            arrayList.add(obj);
                        }
                        adapter=new subCategoryAdapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });

        reference2.whereEqualTo("userid",currentid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                    {


                        if (queryDocumentSnapshots.isEmpty())
                        {
                            placeorder.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            placeorder.setVisibility(View.VISIBLE);
                        }
                    }
                });

        Log.d("cartshopid",""+cartid);
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            //    toolbar.setTitle("Cart");
                recyclerView.setVisibility(View.INVISIBLE);
                placeorder.setVisibility(View.INVISIBLE);
                Fragment fragment=new cart_fragment();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction transaction=fm.beginTransaction();
                transaction.replace(R.id.ghjf,fragment);
                transaction.commit();


            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }
}
