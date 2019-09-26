package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Adapters.AdapterFav;
import com.example.mybookstore.Models.ModelFav;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.DbSqlite;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private TextView txtTitle;
    private ImageView imgBackButton;
    private RecyclerView recyclerView;
    private AdapterFav adapterFav;
    private List<ModelFav> modelFavs;
    private DbSqlite dbSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        findViews();
        onClicks();
        recyvlerInit();
    }

    private void recyvlerInit() {

        dbSqlite = new DbSqlite(FavoriteActivity.this);
        modelFavs = dbSqlite.showData();


        adapterFav = new AdapterFav(FavoriteActivity.this, modelFavs);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
        recyclerView.setAdapter(adapterFav);
        recyclerView.hasFixedSize();
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
}
