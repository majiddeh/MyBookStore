package com.example.mybookstore.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    private SliderLayout sliderLayout;
    private AppBarLayout appBarLayout;
    private ImageView imgBack,imgShopCart;
    private TextView txtTitle,txtAuthor,txtPublisher,txtDesc,txtPrice,txtNext,txtPriceOff,txtCounCart;
    private ApiServices apiServices = new ApiServices(ShowActivity.this);
    private CardView addToShoCart;
    private String id,titlee,imagee,pricee,phone,offPrice;
    private int counter;
    private boolean b =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(ShowActivity.this);
        phone = userSharedPrefrences.getUserLoginInfo();

        findViews();
        appBarLayoutEdit();
        initializePage();
        sliderInitialize();
        onClicks();

    }

    private void onClicks() {
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b){
                    txtDesc.setSingleLine(false);
                    txtNext.setText("بستن");
                    b=false;
                }else {
                    txtDesc.setLines(2);
                    txtNext.setText("ادامه مطلب");
                    b=true;
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addToShoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phone.equals("ورود/عضویت")){
                    Toast.makeText(ShowActivity.this, "لطفا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                }else {

                    if (!offPrice.equals("0")){
                        apiServices.AddToShopCart(id, titlee, imagee, offPrice, phone, new ApiServices.OnShopCartAdd() {
                            @Override
                            public void onShopCart(int responseStatus) {
                                switch (responseStatus){
                                    case 218:
                                        Toast.makeText(ShowActivity.this, "محصول به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0:
                                        Toast.makeText(ShowActivity.this, "سبد بروز شد", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                counter = counter +1;
                                txtCounCart.setText((counter)+"");
                            }
                        });

                    }else {
                        apiServices.AddToShopCart(id, titlee, imagee, pricee, phone, new ApiServices.OnShopCartAdd() {
                            @Override
                            public void onShopCart(int responseStatus) {
                                switch (responseStatus){
                                    case 218:
                                        Toast.makeText(ShowActivity.this, "محصول به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0:
                                        Toast.makeText(ShowActivity.this, "سبد بروز شد", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                counter = counter +1;
                                txtCounCart.setText((counter)+"");
                            }
                        });
                    }
                }
            }
        });

        imgShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowActivity.this,BasketActivity.class));
            }
        });
    }

    private void initializePage() {
        final Typeface typeface = Typeface.createFromAsset(getAssets(),"Vazir-Medium-FD-WOL.ttf");

        apiServices.GetCount(phone, new ApiServices.OnCountReceived() {
            @Override
            public void onCount(String count) {
                txtCounCart.setText(count);
                txtCounCart.setTypeface(typeface);
                counter= Integer.parseInt(count);
            }
        });


        apiServices.GetProductInfo(id,new ApiServices.OnProductInfoReceived() {
            @Override
            public void onInfoReceived(String desc, String title, String visit, String price, String publisher, String author, String image) {
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                titlee= title;
                imagee = image;
                pricee = price;
                txtPriceOff.setVisibility(View.GONE);
                if (!offPrice.equals("0")){
                    txtPriceOff.setText(decimalFormat.format(Integer.valueOf(offPrice))+" "+"تومان");
                    txtPriceOff.setTypeface(typeface);
                    txtPriceOff.setVisibility(View.VISIBLE);
                    txtPrice.setTextColor(Color.RED);
                    txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                txtPrice.setText(decimalFormat.format(Integer.valueOf(pricee))+" "+"تومان");
                txtPrice.setTypeface(typeface);
                txtTitle.setText(titlee);
                txtTitle.setTypeface(typeface);
                txtPublisher.setText(publisher);
                txtPublisher.setTypeface(typeface);
                txtDesc.setText(desc);
                txtDesc.setTypeface(typeface);
                txtAuthor.setText(author);
                txtAuthor.setTypeface(typeface);

            }
        });
    }

    private void appBarLayoutEdit() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int scroll = -(i);
                Log.i("scrol",i+"");
                if (scroll >= 442){
                    imgBack.setColorFilter(Color.rgb(255,255,255));
                    imgShopCart.setColorFilter(Color.rgb(255,255,255));
                    txtCounCart.setTextColor(Color.rgb(255,255,255));
                }else {
                    imgBack.setColorFilter(Color.rgb(189,189,189));
                    imgShopCart.setColorFilter(Color.rgb(189,189,189));
                    txtCounCart.setTextColor(Color.rgb(189,189,189));
                }

            }
        });
    }

    private void findViews() {
        offPrice=getIntent().getStringExtra(Put.offPrice);
        txtCounCart=findViewById(R.id.txt_count_cart);
        addToShoCart=findViewById(R.id.card_addto_shopcart);
        id= getIntent().getStringExtra(Put.id);
        txtNext = findViewById(R.id.txt_continue);
        txtAuthor = findViewById(R.id.txt_author_show);
        txtPrice = findViewById(R.id.txt_price_show_activirt);
        txtPriceOff = findViewById(R.id.txt_price_off_show_activirt);
        txtPublisher = findViewById(R.id.txt_publisher_show);
        txtTitle = findViewById(R.id.txt_title_show_activity);
        txtDesc = findViewById(R.id.txt_desc_show);
        sliderLayout = findViewById(R.id.slider_activity_show);
        appBarLayout=findViewById(R.id.app_bar_layout_show_activity);
        imgBack = findViewById(R.id.imgback_showactivity);
        imgShopCart = findViewById(R.id.img_shop_cart_showactivity);
    }

    private void sliderInitialize() {
//        id= getIntent().getStringExtra(Put.id);
        apiServices.GetProductImage(id,new ApiServices.OnImageProductReceived() {
            @Override
            public void onImageReceived(ArrayList<String> pics) {
                for (int i = 0; i < pics.size(); i++) {
                    TextSliderView textSliderView = new TextSliderView(ShowActivity.this);
                    textSliderView
                            .image(pics.get(i).replace(Links.LOCALHOST,Links.Link))
//                    .setOnSliderClickListener(this)
                            .empty(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .setScaleType(BaseSliderView.ScaleType.Fit);

//                    textSliderView.bundle(new Bundle());
//                    textSliderView.getBundle()
//                            .putString("extra", keyname);
                    sliderLayout.setDuration(3000);
                    sliderLayout.addSlider(textSliderView);
                }

            }
        });


    }
}
