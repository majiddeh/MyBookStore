package com.example.mybookstore.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.DecimalFormat;
import java.util.List;

public class BasketActivity extends AppCompatActivity {
    TextView txtTitle,txttotal;
    ImageView imgBackButton;
    LinearLayout lnrBasket;
    CardView cardBasket;
    RecyclerView recyclerViewBasket;
    ApiServices apiServices = new ApiServices(BasketActivity.this);
    ProgressWheel progressWheel;
    AdapterBasket adapterBasket;
    int totalAllPrice = 0;
    String phone,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(BasketActivity.this);
        phone= userSharedPrefrences.getUserName();
        token = userSharedPrefrences.getUserToken();


        finViews();
        initializePage();
        onClicks();




    }

    private void initializePage() {
        apiServices.CartReceived(progressWheel,token, new ApiServices.OnCartReceived() {
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

        cardBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasketActivity.this,PayConfirmActivity.class);
                intent.putExtra(Put.token,token);
                startActivity(intent);
            }
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(Links.ZARRINPAL+"?Amount="+"1000"+"&Email="+"majiddehghan74@gmail.com"+"&Description="+"سفارش کتاب"));
//                try {
//                    startActivity(intent);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
////                Intent intent = new Intent(BasketActivity.this,WebGateActivity.class);
////                intent.putExtra(Put.total,totalAllPrice);
////                startActivity(intent);
//            }
        });
    }

    private void finViews() {
        progressWheel = findViewById(R.id.progress_wheel);
        recyclerViewBasket=findViewById(R.id.recyclerbasket);
        cardBasket=findViewById(R.id.card_basket);
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
