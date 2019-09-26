package com.example.mybookstore.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mybookstore.Adapters.AdapterViewPager;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;

import java.util.List;

public class CatActivity_ViewPager extends AppCompatActivity {

    ImageView imgBack;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterViewPager adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat__view_pager);

        findViews();
        onClicks();
        setUpViewPager();
    }

    private void setUpViewPager() {


        ApiServices apiServices = new ApiServices(CatActivity_ViewPager.this);
        apiServices.categoryReceiveViewPager(new ApiServices.OnCatStringReceived() {
            @Override
            public void onCatReceived(List<String> strings) {
                adapterViewPager = new AdapterViewPager(CatActivity_ViewPager.this,strings);
                viewPager.setAdapter(adapterViewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        });

    }

    private void onClicks() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        imgBack = findViewById(R.id.img_back_second_toolbar);
        tabLayout = findViewById(R.id.tablayout_cat);
        viewPager = findViewById(R.id.view_pager_cat);
    }
}
