package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Adapters.AdapterFav;
import com.example.mybookstore.Models.ModelFav;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private TextView txtTitle;
    private ImageView imgBackButton;
    private RecyclerView recyclerView;
    private AdapterFav adapterFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        findViews();
        onClicks();
        recyvlerInit();
    }

    private void recyvlerInit() {

//        adapterFav = new AdapterFav(FavoriteActivity.this, modelFavs);
//        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
//        recyclerView.setAdapter(adapterFav);
//        recyclerView.hasFixedSize();
    }

    private void onClicks() {
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("لیست علاقه مندی ها");
        imgBackButton = findViewById(R.id.img_back_second_toolbar);
        recyclerView = findViewById(R.id.recycler_fav);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ApiServices apiServices = new ApiServices(FavoriteActivity.this);
        apiServices.FavReceived(getIntent().getStringExtra(Put.username), new ApiServices.OnFavtReceived() {
            @Override
            public void onReceived(List<ModelFav> modelFavs) {
                adapterFav = new AdapterFav(FavoriteActivity.this, modelFavs);
                recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
                recyclerView.setAdapter(adapterFav);
                recyclerView.hasFixedSize();
            }
        });


    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
