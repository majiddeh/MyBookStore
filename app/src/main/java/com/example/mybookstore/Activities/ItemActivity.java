package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Adapters.AdapterItemCat;
import com.example.mybookstore.Adapters.AdapterItemProduct;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterItemProduct adapterItemProduct ;
    private TextView txtTitle;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        id= getIntent().getStringExtra(Put.id);
        String title = getIntent().getStringExtra(Put.name);

        findViews();
        initializePage();
        txtTitle.setText(getString(R.string.group) + " " + title);


    }

    private void initializePage() {
        ApiServices apiServices = new ApiServices(ItemActivity.this);
        apiServices.GetItemProduct(id, new ApiServices.OnItemProductReceived() {
            @Override
            public void onItemReceived(List<ModelItemProduct> modelItemProducts) {
                adapterItemProduct = new AdapterItemProduct(ItemActivity.this,modelItemProducts);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapterItemProduct);
            }
        });

    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_item);
        txtTitle=findViewById(R.id.txt_title_toolbar_second);
        ImageView imgBackButton = findViewById(R.id.img_back_second_toolbar);
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
