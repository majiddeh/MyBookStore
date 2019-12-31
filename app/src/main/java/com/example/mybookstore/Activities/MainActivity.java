package com.example.mybookstore.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.mybookstore.Adapters.AdapterBanner;
import com.example.mybookstore.Adapters.AdapterCategory;
import com.example.mybookstore.Adapters.AdapterCategoryLittleView;
import com.example.mybookstore.Adapters.AdapterOff;
import com.example.mybookstore.Adapters.AdapterProduct;
import com.example.mybookstore.Models.ModelBanner;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.Models.ModelSlider;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements  BaseSliderView.OnSliderClickListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    ImageView imgShopCart,imgSearch;
    CircleImageView circleImageView;
    SliderLayout sliderLayout;
    CardView cardCategory;
    RecyclerView recyclerOff, recyclerCategory,recyclerOnly,
            recyclerMostVisit,recyclerMostSold,recycBanner,recyBannerBig,recycCatLittle;
    AdapterOff adapterOff;
    TextView txtNaviLogin,txtCount,navAbout, navCat,navBasket,navAccount,
            navFav,txtListOffs,txtListOnly,txtListMostVisit,txtListMostSold;
    LinearLayout specialOffersLable;
    String username,token;
    ApiServices apiServices = new ApiServices(MainActivity.this);
    UserSharedPrefrences userSharedPrefrences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        findViews();
        setUpToolbar();
        sliderInitialize();
        recyclerInitialize();
        onCliclks();
        setUpNavigation();
    }

    private void findViews() {
        userSharedPrefrences = new UserSharedPrefrences(MainActivity.this);

        username = userSharedPrefrences.getUserName();
        token=userSharedPrefrences.getUserToken();

        recyclerOff = findViewById(R.id.offlist_recycler);
        sliderLayout = findViewById(R.id.slider);
        cardCategory = findViewById(R.id.card_category_main);
        specialOffersLable = findViewById(R.id.specialOffers_lable);
        recyclerCategory = findViewById(R.id.recycler_categoryList_main_activity);
        recyclerOnly = findViewById(R.id.recycler_only);
        recyclerMostVisit = findViewById(R.id.recycler_most_visited);
        recyclerMostSold = findViewById(R.id.recycler_most_sold);
        recycCatLittle = findViewById(R.id.recyc_cat_little);
        txtCount=findViewById(R.id.txt_count_cart);
        drawer = findViewById(R.id.drawer_layout);
        imgShopCart=findViewById(R.id.img_shop_cart);
        imgSearch=findViewById(R.id.img_search);
        toolbar = findViewById(R.id.toolbar);

        txtListMostSold = findViewById(R.id.txt_list_most_sold);
        txtListMostVisit = findViewById(R.id.txt_list_most_visit);
        txtListOnly = findViewById(R.id.txt_list_only);
        txtListOffs = findViewById(R.id.txt_list_offs);

        recycBanner = findViewById(R.id.recycler_banner);
        recyBannerBig = findViewById(R.id.recycler_banner_big);

        navAbout = findViewById(R.id.nav_about);
        navBasket = findViewById(R.id.nav_basket);
        navCat = findViewById(R.id.nav_list_category);
        navAccount = findViewById(R.id.nav_account);
        navFav = findViewById(R.id.nav_favorite);
        drawer = findViewById(R.id.drawer_layout);


        circleImageView = findViewById(R.id.img_circle_nav_header);
        if (userSharedPrefrences.getUserImaje().equals("")){
                    Picasso.with(MainActivity.this).load(R.drawable.avatar).
                error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(circleImageView);
        }else {
            Picasso.with(MainActivity.this).load(userSharedPrefrences.getUserImaje()).
                    error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(circleImageView);
        }

        txtNaviLogin = findViewById(R.id.txt_nav_login);
        txtNaviLogin.setText(username);




    }

    private void setUpNavigation() {

        if (!username.isEmpty()) {
            txtNaviLogin.setText(username);
        }
        navAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNaviLogin.getText().equals("ورود/عضویت")) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Put.REQUEST_CODE);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    drawer.closeDrawer(GravityCompat.START);
                    } else {
                    startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class), Put.REQUEST_EXIT);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    drawer.closeDrawer(GravityCompat.START);
                    }

            }
        });
        navCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CategoryActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNaviLogin.getText().equals("ورود/عضویت")) {
                    drawer.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "لطفا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                } else {
                        startActivity(new Intent(MainActivity.this, BasketActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    drawer.closeDrawer(GravityCompat.START);
                    }

            }
        });
        navAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
        navFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNaviLogin.getText().equals("ورود/عضویت")) {
                    Toast.makeText(MainActivity.this, "وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                    intent.putExtra(Put.username, username);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                }
            }
        });


    }

    private void onCliclks() {
        txtNaviLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNaviLogin.getText().equals("ورود/عضویت")) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Put.REQUEST_CODE);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class), Put.REQUEST_EXIT);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

        cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CatActivity_ViewPager.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        imgShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.equals("ورود/عضویت")){
                    Toast.makeText(MainActivity.this, "لطفا وارد حساب خود شوید", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(MainActivity.this,BasketActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }

            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        txtListOffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FullProductActivity.class);
                intent.putExtra(Put.links,Links.GET_OFF);
                startActivity(intent);
            }
        });

        txtListOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FullProductActivity.class);
                intent.putExtra(Put.links,Links.GET_ONLY);
                startActivity(intent);
            }
        });

        txtListMostVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FullProductActivity.class);
                intent.putExtra(Put.links,Links.GET_MOST_VISIT);
                startActivity(intent);
            }
        });

        txtListMostSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FullProductActivity.class);
                intent.putExtra(Put.links,Links.GET_MOST_VISIT);
                startActivity(intent);
            }
        });

    }

    private void recyclerInitialize() {

        apiServices.offReceived(Links.GET_OFF,new ApiServices.OnOffReceived() {
            @Override
            public void onOffReceived(List<ModelOff_Only_MostVisit> modelOffOnlies) {
                if (modelOffOnlies.size() == 0) {
                    specialOffersLable.setVisibility(View.INVISIBLE);
                    recyclerOff.setVisibility(View.INVISIBLE);
                } else {
                    adapterOff = new AdapterOff(modelOffOnlies, MainActivity.this);
                    recyclerOff.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerOff.hasFixedSize();
                    recyclerOff.setAdapter(adapterOff);

                }
            }
        });

        apiServices.categoryReceive(new ApiServices.OnCategoryReceived() {
            @Override
            public void onCatReceived(List<ModelCategory> modelCategories) {
                AdapterCategoryLittleView adapterCategoryLittleView = new AdapterCategoryLittleView(MainActivity.this, modelCategories);
                recycCatLittle.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recycCatLittle.hasFixedSize();
                recycCatLittle.setAdapter(adapterCategoryLittleView);
            }
        });

        apiServices.categoryReceive(new ApiServices.OnCategoryReceived() {
            @Override
            public void onCatReceived(List<ModelCategory> modelCategories) {
                AdapterCategory adapterCategory = new AdapterCategory(MainActivity.this, modelCategories);
                recyclerCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerCategory.hasFixedSize();
                recyclerCategory.setAdapter(adapterCategory);
            }
        });

        apiServices.onlyReceived(Links.GET_ONLY,new ApiServices.OnOnlyReceived() {
            @Override
            public void onOnlyReceived(List<ModelOff_Only_MostVisit> modelOnlies) {
                AdapterProduct adapterProduct = new AdapterProduct(MainActivity.this,modelOnlies);
                recyclerOnly.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                recyclerOnly.hasFixedSize();
                recyclerOnly.setAdapter(adapterProduct);
            }
        });

        apiServices.MostvisitReceived(Links.GET_MOST_VISIT,new ApiServices.OnMostVisitReceived() {
            @Override
            public void onMostVisit(List<ModelOff_Only_MostVisit> modelMostVisit) {
                AdapterProduct adapterProduct = new AdapterProduct(MainActivity.this,modelMostVisit);
                recyclerMostVisit.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                recyclerMostVisit.hasFixedSize();
                recyclerMostVisit.setAdapter(adapterProduct);
            }
        });

        apiServices.MostSoldReceived(Links.GET_MOST_SOLD,new ApiServices.OnMostSoldReceived() {
            @Override
            public void onSold(List<ModelOff_Only_MostVisit> modelSold) {
                AdapterProduct adapterProduct = new AdapterProduct(MainActivity.this,modelSold);
                recyclerMostSold.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                recyclerMostSold.hasFixedSize();
                recyclerMostSold.setAdapter(adapterProduct);
            }
        });

        apiServices.GetBanners(new ApiServices.OnBannerReceived() {
            @Override
            public void onBanner(List<ModelBanner> modelBanners) {
                AdapterBanner adapterBanner = new AdapterBanner(MainActivity.this,modelBanners);
                recycBanner.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                recycBanner.hasFixedSize();
                recycBanner.setAdapter(adapterBanner);
            }
        });

        apiServices.GetBannerBig(new ApiServices.OnBannerReceived() {
            @Override
            public void onBanner(List<ModelBanner> modelBanners) {
                AdapterBanner adapterBanner = new AdapterBanner(MainActivity.this,modelBanners);
                recyBannerBig.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
                recyBannerBig.hasFixedSize();
                recyBannerBig.setAdapter(adapterBanner);
            }
        });



    }

    private void sliderInitialize() {
        apiServices.GetSliders(new ApiServices.OnSliderReceived() {
            @Override
            public void onSlider(List<ModelSlider> modelSliders) {
                HashMap<String, String> url_image = new HashMap<>();
                for (int i = 0; i < modelSliders.size(); i++) {
                    url_image.put(modelSliders.get(i).getName(),modelSliders.get(i).getImage());
                }
                for (int i = 0; i < url_image.keySet().size(); i++) {
                    String keyname = url_image.keySet().toArray()[i].toString();
                    TextSliderView textSliderView = new TextSliderView(MainActivity.this);
                    textSliderView
                            .description(keyname)
                            .image(url_image.get(keyname))
                            .setOnSliderClickListener(MainActivity.this)
                            .empty(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", keyname);
                    sliderLayout.setDuration(3000);
                    sliderLayout.addSlider(textSliderView);
                }
            }
        });
//        HashMap<String, String> url_image = new HashMap<>();
//        url_image.put("image1",Links.Link_HOST + "/samed_bookstore/uploads/banners/banner4.jpg");
//        url_image.put("image2",Links.Link_HOST + "/samed_bookstore/uploads/banners/banner1.jpg");
//        url_image.put("image3",Links.Link_HOST + "/samed_bookstore/uploads/banners/banner7.jpg");
//        url_image.put("image4",Links.Link_HOST + "/samed_bookstore/uploads/banners/banner8.jpg");
//
//        for (int i = 0; i < url_image.keySet().size(); i++) {
//            String keyname = url_image.keySet().toArray()[i].toString();
//            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
//            textSliderView
////                    .description(keyname)
//                    .image(url_image.get(keyname))
//                    .setOnSliderClickListener(this)
//                    .empty(R.drawable.placeholder)
//                    .error(R.drawable.placeholder)
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra", keyname);
//            sliderLayout.setDuration(3000);
//            sliderLayout.addSlider(textSliderView);
//        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (username != null && username.equals("ورود/عضویت")){
            imgShopCart.setColorFilter(Color.rgb(170,170,170));
            txtCount.setVisibility(View.GONE);
        }



    }




    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Put.REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String username = data.getStringExtra(Put.username);
                String image = data.getStringExtra(Put.image);
                txtNaviLogin.setText("خوش آمدید  " + username);
                Picasso.with(MainActivity.this).load(image)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(circleImageView);
                recreate();
            }
        } else if (requestCode == Put.REQUEST_EXIT && resultCode == RESULT_OK) {
            if (data != null) {
                String username = data.getStringExtra(Put.username);
                String image = data.getStringExtra(Put.image);
                if (image.equals("")){
                    Picasso.with(MainActivity.this).load(R.drawable.banner2).
                            error(R.drawable.placeholder)
                            .placeholder(R.drawable.placeholder)
                            .into(circleImageView);
                }else {
                    Picasso.with(MainActivity.this).load(userSharedPrefrences.getUserImaje()).
                            error(R.drawable.placeholder)
                            .placeholder(R.drawable.placeholder)
                            .into(circleImageView);
                }
                txtNaviLogin.setText(username);
                recreate();
            }

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        String s = slider.getBundle().get("extra") + "";

//        switch (s) {
//            case ("image1"):
//                Toast.makeText(this, "عکس شماره 1", Toast.LENGTH_SHORT).show();
//                break;
//            case ("image2"):
//                Toast.makeText(this, "عکس شماره 2", Toast.LENGTH_SHORT).show();
//                break;
//            case ("image3"):
//                Toast.makeText(this, "عکس شماره 3", Toast.LENGTH_SHORT).show();
//                break;
//            case ("image4"):
//                Toast.makeText(this, "عکس شماره 4", Toast.LENGTH_SHORT).show();
//                break;
//
//
//        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiServices.GetCount(token, new ApiServices.OnCountReceived() {
            @Override
            public void onCount(String count) {
                txtCount.setText(count);
            }
        });
    }
}
