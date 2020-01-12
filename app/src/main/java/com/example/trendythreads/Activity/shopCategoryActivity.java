package com.example.trendythreads.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trendythreads.AllModel.mnb;
import com.example.trendythreads.R;
import com.example.trendythreads.Adapter.adapter;
import com.example.trendythreads.Adapter.shopCategoryAdapter;
import com.example.trendythreads.AllModel.shopcatgory;
import com.example.trendythreads.AllModel.slidermodel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class shopCategoryActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<shopcatgory> arrayList;
    int position;
  //  ProgressBar progressBar;
    FirebaseAuth mauth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference1;

    String[] a1={"Daal","White chana","Rajama"};
    int[] a2={
            R.drawable.pulses,R.drawable.chanapulse,R.drawable.kidneybeans
    };
    ArrayList<slidermodel> slider_arrayList;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding);

       progressBar=findViewById(R.id.progress);
        Sprite doublebounce=new DoubleBounce();
        progressBar.setIndeterminateDrawable(doublebounce);
        ///

        SliderView view=findViewById(R.id.imageSlider);
        slider_arrayList=new ArrayList<>();
        for (int i=0;i<a1.length;i++)
        {
            slidermodel model=new slidermodel();
            model.setImg(a2[i]);
            model.setText(a1[i]);
            slider_arrayList.add(model);
        }
        com.example.trendythreads.Adapter.adapter as=new adapter(this,slider_arrayList);
        view.setSliderAdapter(as);
        view.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        view.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        view.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        view.setIndicatorSelectedColor(Color.WHITE);
        view.setIndicatorUnselectedColor(Color.GRAY);
        view.setScrollTimeInSec(2); //set scroll delay in seconds :
        view.startAutoCycle();

        mnb o=new mnb();
        String s=o.getShopid();
        Log.d("zxc",""+s);
        ///




        mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        String currentid=user.getUid();
        Log.d("curret",currentid);
        Bundle b=getIntent().getExtras();
        int postion=b.getInt("pos");
        postion=postion+1;
        reference1=db.collection("shopActivity").document("shop"+postion).collection("items");
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
                            shopcatgory obj=new shopcatgory();
                            obj.setItemName(documentSnapshot.getString("itemname"));
                            obj.setItemImage(documentSnapshot.getString("imgUrl"));
                            arrayList.add(obj);
                        }
                        adapter=new shopCategoryAdapter(getApplicationContext(),arrayList);
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
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }


}
