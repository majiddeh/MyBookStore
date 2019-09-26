package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Adapters.AdapterItemProduct;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ImageView imgBackButton;
    private RecyclerView recyBannerItem;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        finViews();
        onClicks();
        initPage();

    }

    private void initPage() {
        String id = String.valueOf(getIntent().getIntExtra(Put.id,0));
        apiServices = new ApiServices(BannerActivity.this);
        apiServices.GetItemBanner(id, new ApiServices.OnItemBannerReceived() {
            @Override
            public void onItemReceived(List<ModelItemProduct> modelItemProducts) {
                AdapterItemProduct adapterItemProduct = new AdapterItemProduct(BannerActivity.this,modelItemProducts);
                recyBannerItem.setLayoutManager(new LinearLayoutManager(BannerActivity.this));
                recyBannerItem.setAdapter(adapterItemProduct);
                recyBannerItem.hasFixedSize();
            }
        });
    }

    private void finViews() {
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("محصولات");
        imgBackButton = findViewById(R.id.img_back_second_toolbar);
        recyBannerItem = findViewById(R.id.recycler_banner_item);
    }

    private void onClicks() {
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
