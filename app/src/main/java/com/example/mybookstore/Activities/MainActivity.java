package com.example.mybookstore.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
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
import com.example.mybookstore.Adapters.AdapterCategory;
import com.example.mybookstore.Adapters.AdapterOff;
import com.example.mybookstore.Adapters.AdapterProduct;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    ImageView imgShopCart,imgSearch;
    SliderLayout sliderLayout;
    CardView cardCategory;
    RecyclerView recyclerOff, recyclerCategory,recyclerOnly,recyclerMostVisit,recyclerMostSold;
    AdapterOff adapterOff;
    TextView txtNaviLogin,txtCount;
    LinearLayout specialOffersLable;
    NavigationView navigationView;
    String phone;
    ApiServices apiServices = new ApiServices(MainActivity.this);

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
        txtNaviLoginInitial();
    }

    private void findViews() {
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),"Vazir-Medium-FD-WOL.ttf");
        txtCount=findViewById(R.id.txt_count_cart);
        txtCount.setTypeface(typeface);
        drawer = findViewById(R.id.drawer_layout);
        imgShopCart=findViewById(R.id.img_shop_cart);
        imgSearch=findViewById(R.id.img_search);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        View navi = navigationView.getHeaderView(0);
        txtNaviLogin = navi.findViewById(R.id.txt_nav_login);
        txtNaviLogin.setText(phone);
        recyclerOff = findViewById(R.id.offlist_recycler);
        sliderLayout = findViewById(R.id.slider);
        cardCategory = findViewById(R.id.card_category_main);
        specialOffersLable = findViewById(R.id.specialOffers_lable);
        recyclerCategory = findViewById(R.id.recycler_categoryList_main_activity);
        recyclerOnly = findViewById(R.id.recycler_only);
        recyclerMostVisit = findViewById(R.id.recycler_most_visited);
        recyclerMostSold = findViewById(R.id.recycler_most_sold);
        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(MainActivity.this);
        phone = userSharedPrefrences.getUserLoginInfo();

    }

    private void txtNaviLoginInitial() {

        if (!phone.isEmpty()) {
            txtNaviLogin.setText(phone);
        }
    }

    private void onCliclks() {


        txtNaviLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNaviLogin.getText().equals("ورود/عضویت")) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Put.REQUEST_CODE);
                } else {
                    startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class), Put.REQUEST_EXIT);
                }
            }
        });

        cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
            }
        });

        imgShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.equals("ورود/عضویت")){
                    Toast.makeText(MainActivity.this, "لطفا وارد حساب خود شوید", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(MainActivity.this,BasketActivity.class));
                }

            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

    }

    private void recyclerInitialize() {

        apiServices.offReceived(new ApiServices.OnOffReceived() {
            @Override
            public void onOffReceived(List<ModelOff_Only_MostVisit> modelOffOnlies) {
                if (modelOffOnlies.size() == 0) {
                    specialOffersLable.setVisibility(View.INVISIBLE);
                    recyclerOff.setVisibility(View.INVISIBLE);
                } else {
                    adapterOff = new AdapterOff(modelOffOnlies, getApplicationContext());
                    recyclerOff.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerOff.setAdapter(adapterOff);

                }
            }
        });

        apiServices.categoryReceive(new ApiServices.OnCategoryReceived() {
            @Override
            public void onCatReceived(List<ModelCategory> modelCategories) {
                AdapterCategory adapterCategory = new AdapterCategory(MainActivity.this, modelCategories);
                recyclerCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerCategory.setAdapter(adapterCategory);
            }
        });

        apiServices.onlyReceived(new ApiServices.OnOnlyReceived() {
            @Override
            public void onOnlyReceived(List<ModelOff_Only_MostVisit> modelOnlies) {
                AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(),modelOnlies);
                recyclerOnly.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerOnly.setAdapter(adapterProduct);
            }
        });

        apiServices.MostvisitReceived(new ApiServices.OnMostVisitReceived() {
            @Override
            public void onMostVisit(List<ModelOff_Only_MostVisit> modelMostVisit) {
                AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(),modelMostVisit);
                recyclerMostVisit.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerMostVisit.setAdapter(adapterProduct);
            }
        });

        apiServices.MostSoldReceived(new ApiServices.OnMostSoldReceived() {
            @Override
            public void onSold(List<ModelOff_Only_MostVisit> modelSold) {
                AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(),modelSold);
                recyclerMostSold.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerMostSold.setAdapter(adapterProduct);
            }
        });



    }

    private void sliderInitialize() {
        HashMap<String, String> url_image = new HashMap<>();
        url_image.put("image1", "http://" + Links.Link + "/samed_bookstore/uploads/banners/banner4.jpg");
        url_image.put("image2", "http://" + Links.Link + "/samed_bookstore/uploads/banners/banner6.jpg");
        url_image.put("image3", "http://" + Links.Link + "/samed_bookstore/uploads/banners/banner7.jpg");
        url_image.put("image4", "http://" + Links.Link + "/samed_bookstore/uploads/banners/banner8.jpg");

        for (int i = 0; i < url_image.keySet().size(); i++) {
            String keyname = url_image.keySet().toArray()[i].toString();
            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
            textSliderView.description(keyname)
                    .image(url_image.get(keyname))
                    .setOnSliderClickListener(this)
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (phone != null && phone.equals("ورود/عضویت")){
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
                String phone = data.getStringExtra(Put.phone);
                txtNaviLogin.setText("خوش آمدید  " + phone);
                recreate();
            }
        } else if (requestCode == Put.REQUEST_EXIT && resultCode == RESULT_OK) {
            if (data != null) {
                String phone = data.getStringExtra(Put.phone);
                txtNaviLogin.setText(phone);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String s = slider.getBundle().get("extra") + "";

        switch (s) {
            case ("image1"):
                Toast.makeText(this, "عکس شماره 1", Toast.LENGTH_SHORT).show();
                break;
            case ("image2"):
                Toast.makeText(this, "عکس شماره 2", Toast.LENGTH_SHORT).show();
                break;
            case ("image3"):
                Toast.makeText(this, "عکس شماره 3", Toast.LENGTH_SHORT).show();
                break;
            case ("image4"):
                Toast.makeText(this, "عکس شماره 4", Toast.LENGTH_SHORT).show();
                break;


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiServices.GetCount(phone, new ApiServices.OnCountReceived() {
            @Override
            public void onCount(String count) {
                txtCount.setText(count);
            }
        });
    }
}
