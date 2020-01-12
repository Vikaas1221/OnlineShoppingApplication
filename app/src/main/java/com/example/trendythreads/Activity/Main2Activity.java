package com.example.trendythreads.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.trendythreads.R;
import com.example.trendythreads.fragment.cart_fragment;
import com.example.trendythreads.fragment.home_fragment;
import com.example.trendythreads.fragment.profile_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {
   // ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom);
      //  toolbar=getSupportActionBar();
      //  toolbar.setTitle("Shop");
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment()).commit();
    }
    public BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {
            Fragment selectedfragment=null;
            switch (menuItem.getItemId())
            {
                case R.id.home_bottom:

                    selectedfragment=new home_fragment();
                   // toolbar.setTitle("Shop");
                    break;
                case R.id.cart_bottom:
                    selectedfragment=new cart_fragment();
                  //  toolbar.setTitle("Cart");
                    break;
                case R.id.profile_bottom:
                    selectedfragment=new profile_fragment();
                  //  toolbar.setTitle("Account");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
            return true;
        }
    };

}
