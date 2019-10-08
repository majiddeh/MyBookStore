package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mybookstore.Adapters.AdapterBasket;
import com.example.mybookstore.Models.ModelBasket;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.UserSharedPrefrences;

import java.text.DecimalFormat;
import java.util.List;

public class BasketActivity extends AppCompatActivity {
    TextView txtTitle,txttotal;
    ImageView imgBackButton;
    LinearLayout lnrBasket;
    RecyclerView recyclerViewBasket;
    ApiServices apiServices = new ApiServices(BasketActivity.this);
    AdapterBasket adapterBasket;
    int totalAllPrice = 0;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(BasketActivity.this);
        phone= userSharedPrefrences.getUserName();

        finViews();
        initializePage();
        onClicks();




    }

    private void initializePage() {
        apiServices.CartReceived(phone, new ApiServices.OnCartReceived() {
            @Override
            public void onReceived(List<ModelBasket> modelBaskets,int totalPrice) {
                adapterBasket = new AdapterBasket(BasketActivity.this,modelBaskets);
                adapterBasket.setOnloadPrice(new AdapterBasket.OnloadPrice() {
                    @Override
                    public void onloadPrice() {
                        recreate();
                    }
                });
                recyclerViewBasket.setLayoutManager(new LinearLayoutManager(BasketActivity.this));
                recyclerViewBasket.setAdapter(adapterBasket);
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                decimalFormat.format(totalPrice);
                txttotal.setText(totalPrice+""+" "+"تومان");


            }
        });


    }

    private void onClicks() {
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void finViews() {
        recyclerViewBasket=findViewById(R.id.recyclerbasket);
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("سبد خرید شما");
        imgBackButton = findViewById(R.id.img_back_second_toolbar);
        lnrBasket=findViewById(R.id.lnrbasket);
        txttotal=findViewById(R.id.txt_total);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
