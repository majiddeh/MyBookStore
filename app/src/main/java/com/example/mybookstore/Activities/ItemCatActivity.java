package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Adapters.AdapterCategory;
import com.example.mybookstore.Adapters.AdapterItemCat;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;

import java.util.List;

public class ItemCatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterItemCat adapterItemCat ;
    private TextView txtTitle;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_cat);
        id= getIntent().getStringExtra(Put.id);
        String title = getIntent().getStringExtra(Put.name);

        findViews();
        initializePage();
        txtTitle.setText(getString(R.string.group) + " " + title);

    }

    private void initializePage() {
        ApiServices apiServices = new ApiServices(ItemCatActivity.this);
        apiServices.ItemcategoryReceive(id,new ApiServices.OnItemCategoryReceived() {
            @Override
            public void onItemReceived(List<ModelCategory> modelCategories) {
                adapterItemCat = new AdapterItemCat(ItemCatActivity.this,modelCategories);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                recyclerView.setAdapter(adapterItemCat);
            }
        });
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_category);
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
