package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Adapters.AdapterCategory;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterCategory adapterCategory ;
    private ImageView imgBackButton;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        findViews();
        txtTitle.setText("دسته بندی محصولات");

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ApiServices apiServices = new ApiServices(CategoryActivity.this);
        apiServices.categoryReceive(new ApiServices.OnCategoryReceived() {
            @Override
            public void onCatReceived(List<ModelCategory> modelCategories) {
                adapterCategory = new AdapterCategory(CategoryActivity.this,modelCategories);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                recyclerView.setAdapter(adapterCategory);
            }
        });




    }





    private void findViews() {
        recyclerView=findViewById(R.id.recycler_category);
        txtTitle=findViewById(R.id.txt_title_toolbar_second);
        imgBackButton=findViewById(R.id.img_back_second_toolbar);
    }
}
