package com.example.mybookstore.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.mybookstore.Adapters.AdapterLikes;
import com.example.mybookstore.Models.ModelLikes;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    private SliderLayout sliderLayout;
    private AppBarLayout appBarLayout;
    private ImageView imgBack,imgShopCart,imgFav;
    private TextView txtTitle,txtAuthor,txtPublisher,txtDesc,
            txtTitleToolbar,txtPrice,txtNext,txtPriceOff,txtCounCart,txtRate;
    private ApiServices apiServices = new ApiServices(ShowActivity.this);
    private CardView addToShoCart,cardComments;
    private String id,titlee,imagee,pricee, username,offPrice,visitt,lablee,catt;
    private NestedScrollView scrollView;
    private RecyclerView recyclerViewLikes;
    private ScaleRatingBar ratingBar;
    private int counter;
    private boolean b =true;
    private boolean fav =true;
    private boolean checkUp =true;
    private boolean checkDown =true;
    UserSharedPrefrences userSharedPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        userSharedPrefrences = new UserSharedPrefrences(ShowActivity.this);
        username = userSharedPrefrences.getUserName();

        findViews();
        initializePage();
        appBarLayoutEdit();
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

                if (username.equals("ورود/عضویت")){
                    Toast.makeText(ShowActivity.this, "لطفا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                }else {

                    if (!offPrice.equals("0")){
                        apiServices.AddToShopCart(id, titlee, imagee, offPrice, userSharedPrefrences.getUserToken(), new ApiServices.OnShopCartAdd() {
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
                        apiServices.AddToShopCart(id, titlee, imagee, pricee, userSharedPrefrences.getUserToken(), new ApiServices.OnShopCartAdd() {
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
                if (username.equals("ورود/عضویت")){
                    Toast.makeText(ShowActivity.this, "لطفا وارد حساب خود شوید", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(ShowActivity.this,BasketActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

        cardComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this,ShowCommentActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                intent.putExtra(Put.id,id);
                startActivity(intent);
            }
        });

        //TODO  publisher & Description & Author be Complete

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.equals("ورود/عضویت")){
                    Toast.makeText(ShowActivity.this, "لطفا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                }else {
                    apiServices.AddToFav(id, username, new ApiServices.OnFavAdd() {
                        @Override
                        public void onFav(int responseStatus) {
                            if (responseStatus==218){
                                imgFav.setImageResource(R.drawable.ic_star_black_24dp);
                                imgFav.setColorFilter(ShowActivity.this.getResources().getColor(R.color.gold));
                                fav=false;
                                Toast.makeText(ShowActivity.this, "به لیست علاقه مندی ها اضافه شد", Toast.LENGTH_SHORT).show();
                            }else {
                                fav=true;
                                imgFav.setImageResource(R.drawable.ic_star_border_black_24dp);
                                imgFav.setColorFilter(ShowActivity.this.getResources().getColor(R.color.gray));
                                Toast.makeText(ShowActivity.this, "از لیست علاقه مندی ها حذف شد", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });
    }

    private void initializePage() {

        apiServices.CheckFav(id, username, new ApiServices.OnFavCheck() {
            @Override
            public void onCheck(int responseStatus) {
                if (responseStatus==218){
                    imgFav.setColorFilter(ShowActivity.this.getResources().getColor(R.color.gold));
                    imgFav.setImageResource(R.drawable.ic_star_black_24dp);
                    fav=false;
                }else {

                    imgFav.setColorFilter(ShowActivity.this.getResources().getColor(R.color.gray));
                    imgFav.setImageResource(R.drawable.ic_star_border_black_24dp);
                    fav=true;
                }
            }
        });

        apiServices.GetCount(userSharedPrefrences.getUserToken(), new ApiServices.OnCountReceived() {
            @Override
            public void onCount(String count) {
                txtCounCart.setText(count);
                counter= Integer.parseInt(count);
            }
        });


        apiServices.GetProductInfo(id, new ApiServices.OnProductInfoReceived() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onInfoReceived(String desc, String title, String visit, String price, String finalrating, String publisher, String author, String image,String lable,String cat,String offPrice) {
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                titlee= title;
                imagee = image;
                pricee = price;
                visitt=visit;
                lablee=lable;
                catt=cat;
                txtPriceOff.setVisibility(View.GONE);
                if (!offPrice.equals("0")){
                    txtPriceOff.setText(decimalFormat.format(Float.valueOf(offPrice))+" "+"تومان");
                    txtPriceOff.setVisibility(View.VISIBLE);
                    txtPrice.setTextColor(Color.RED);
                    txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                txtPrice.setText(decimalFormat.format(Integer.valueOf(pricee))+" "+"تومان");
                txtTitle.setText(titlee);
                txtPublisher.setText(publisher);
                ratingBar.setRating(Float.parseFloat(finalrating));
                txtDesc.setText(desc);
                txtAuthor.setText(author);
                txtRate.setText(finalrating+" از "+"5");

            }
        });



//        apiServices.GetLikeProduct( new ApiServices.OnLikeReceived() {
//            @Override
//            public void onLike(List<ModelOff_Only_MostVisit> modelOff_only_mostVisits) {
//                Toast.makeText(ShowActivity.this, "like", Toast.LENGTH_SHORT).show();
//                AdapterProduct adapterProduct = new AdapterProduct(ShowActivity.this,modelOff_only_mostVisits);
//                recyclerViewLikes.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
//                recyclerViewLikes.setAdapter(adapterProduct);
//            }
//        });

        // TODO eslah jeddi niaz dard aslan dorost kar nemikonad fuuuuuuuuuuuck
        catt=getIntent().getStringExtra(Put.cat);
        apiServices.GetLikeProduct(id,catt,new ApiServices.OnLikeReceived() {
            @Override
            public void onLike(List<ModelLikes> modelLikes) {
//                Toast.makeText(ShowActivity.this, "like", Toast.LENGTH_SHORT).show();
                AdapterLikes adapterLikes = new AdapterLikes(ShowActivity.this,modelLikes);
                recyclerViewLikes.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerViewLikes.setAdapter(adapterLikes);
            }
        });

//        apiServices.MostvisitReceived(Links.GET_MOST_SOLD,new ApiServices.OnMostVisitReceived() {
//            @Override
//            public void onMostVisit(List<ModelOff_Only_MostVisit> modelMostVisit) {
////                Toast.makeText(ShowActivity.this, "most", Toast.LENGTH_SHORT).show();
//                AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(),modelMostVisit);
//                recyclerViewSimilar.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
//                recyclerViewSimilar.setAdapter(adapterProduct);
//            }
//        });


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

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Log.i("scrolll",scrollView.getScrollY()+"");
//                int dpSize = convertPX(scrollView.getScrollY(),ShowActivity.this);
//                Log.i("scroldp",dpSize+"");
                if (scrollView.getScrollY() >= 80){
                    if (checkUp){
                        Animation animation = AnimationUtils.loadAnimation(ShowActivity.this,R.anim.toolbar_title_going_up);
                        txtTitleToolbar.startAnimation(animation);
                        txtTitleToolbar.setText(titlee);
                        checkUp =false;
                        checkDown=true;
                    }

                }else {
                    txtTitleToolbar.setText("");
                    checkUp=true;
                    //TODO animation goingDown dorost kar nemikone hatman barrassi shavad...
//                    if (checkDown){
//                        Animation animation = AnimationUtils.loadAnimation(ShowActivity.this,R.anim.toolbar_title_going_down);
//                        txtTitleToolbar.startAnimation(animation);
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                txtTitleToolbar.setText("");
//                            }
//                        },1000);
//
//                        checkUp =true;
//                        checkDown=false;
//                    }


                }
            }
        });
    }

    public static int convertPX (float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px /((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)dp;
    }

    private void findViews() {
        cardComments=findViewById(R.id.card_comments_show_activity);
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
        txtTitleToolbar = findViewById(R.id.txt_title_book_toolbar_show);


        sliderLayout = findViewById(R.id.slider_activity_show);
        appBarLayout=findViewById(R.id.app_bar_layout_show_activity);
        scrollView = findViewById(R.id.scrolview_show_activity);

        imgBack = findViewById(R.id.imgback_showactivity);
        imgShopCart = findViewById(R.id.img_shop_cart_showactivity);
        recyclerViewLikes =findViewById(R.id.recycler_similar);
//        recyclerViewSimilar=findViewById(R.id.recycler_others);
        ratingBar = findViewById(R.id.ratingbar);
        txtRate = findViewById(R.id.txt_rate);
        imgFav = findViewById(R.id.img_favorite);
    }

    private void sliderInitialize() {
//        id= getIntent().getStringExtra(Put.id);
        apiServices.GetProductImage(id,new ApiServices.OnImageProductReceived() {
            @Override
            public void onImageReceived(ArrayList<String> pics) {
                for (int i = 0; i < pics.size(); i++) {
                    TextSliderView textSliderView = new TextSliderView(ShowActivity.this);
                    textSliderView
                            .image(pics.get(i))
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
