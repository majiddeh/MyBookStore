package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Adapters.AdapterCategoryLittleView;
import com.example.mybookstore.Adapters.AdapterOff;
import com.example.mybookstore.Adapters.AdapterProduct;
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;

import java.util.List;

public class FullProductActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ImageView imgBack;
    private ApiServices apiServices;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_product);

        findViews();
        onClicks();
        initPage();

    }

    private void initPage() {
        if (getIntent().getStringExtra(Put.links).equals(Links.GET_OFF)){
            apiServices.offReceived(Links.GET_OFF, new ApiServices.OnOffReceived() {
                @Override
                public void onOffReceived(List<ModelOff_Only_MostVisit> modelOffOnlies) {
                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(),modelOffOnlies);
                    recyclerView.setLayoutManager(new GridLayoutManager(FullProductActivity.this,2));
                    recyclerView.setAdapter(adapterProduct);
                }
            });
        }else {
            apiServices.onlyReceived(getIntent().getStringExtra(Put.links), new ApiServices.OnOnlyReceived() {
                @Override
                public void onOnlyReceived(List<ModelOff_Only_MostVisit> modelOnlies) {
                    AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(),modelOnlies);
                    recyclerView.setLayoutManager(new GridLayoutManager(FullProductActivity.this,2));
                    recyclerView.setAdapter(adapterProduct);
                }
            });
        }
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
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("لیست کامل");
        imgBack = findViewById(R.id.img_back_second_toolbar);
        apiServices = new ApiServices(FullProductActivity.this);
        recyclerView = findViewById(R.id.recycler_ull_products);
    }
}
