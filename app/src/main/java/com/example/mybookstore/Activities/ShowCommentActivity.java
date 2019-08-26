package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Adapters.AdapterComment;
import com.example.mybookstore.Models.ModelComment;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import java.util.ArrayList;
import java.util.List;

public class ShowCommentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterComment adapterComment;
    ImageView imgBack;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);

        findViews();
        initPage();
        onClicks();

    }

    private void onClicks() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPage() {
        ApiServices apiServices = new ApiServices(ShowCommentActivity.this);
        apiServices.GetComment(getIntent().getStringExtra(Put.id),new ApiServices.OnCommentReceived() {
            @Override
            public void onComment(List<ModelComment> modelComments) {
                adapterComment= new AdapterComment(ShowCommentActivity.this,modelComments);
                recyclerView.setLayoutManager(new LinearLayoutManager(ShowCommentActivity.this));
                recyclerView.setAdapter(adapterComment);
                recyclerView.hasFixedSize();
            }
        });
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_comment);
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("نظرات کاربران");
        imgBack = findViewById(R.id.img_back_second_toolbar);

    }
}
