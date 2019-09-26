package com.example.mybookstore.Activities;

import android.app.Dialog;
import android.content.ClipData;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Adapters.AdapterItemCat;
import com.example.mybookstore.Adapters.AdapterItemProduct;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterItemProduct adapterItemProduct ;
    private TextView txtTitle;
    private CardView cardFilter;
    private ImageView imgBackButton ;
    private String id;
    private ApiServices apiServices;
    private List<ModelItemProduct> modelItemProductss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        id= getIntent().getStringExtra(Put.id);
        String title = getIntent().getStringExtra(Put.name);

        findViews();
        initializePage();
        txtTitle.setText(getString(R.string.group) + " " + title);
        onClicks();


    }

    private void onClicks() {
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cardFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogeFilter();
            }
        });
    }

    private void dialogeFilter(){

        final Dialog dialog = new Dialog(ItemActivity.this);
        dialog.setContentView(R.layout.dialoge_filter);

        RadioButton rd1,rd2,rd3,rd4;

        rd1 = dialog.findViewById(R.id.rd1);
        rd2 = dialog.findViewById(R.id.rd2);
        rd3 = dialog.findViewById(R.id.rd3);
        rd4 = dialog.findViewById(R.id.rd4);

        rd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelItemProductss.clear();
                adapterItemProduct.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        apiServices.FilterItem(id, Put.visit, new ApiServices.OnFilterItem() {
                            @Override
                            public void onFilter(List<ModelItemProduct> modelItemProducts) {

                                adapterItemProduct = new AdapterItemProduct(ItemActivity.this,modelItemProducts);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(adapterItemProduct);
                            }
                        });
                        dialog.cancel();
                    }
                },100);
            }
        });
        rd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelItemProductss.clear();
                adapterItemProduct.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        apiServices.FilterItem(id, "sale", new ApiServices.OnFilterItem() {
                            @Override
                            public void onFilter(List<ModelItemProduct> modelItemProducts) {
                                modelItemProductss =modelItemProducts;

                                adapterItemProduct = new AdapterItemProduct(ItemActivity.this,modelItemProductss);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(adapterItemProduct);
                            }
                        });
                        dialog.cancel();
                    }
                },100);
            }
        });
        rd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelItemProductss.clear();
                adapterItemProduct.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        apiServices.FilterItem(id, "priceUp", new ApiServices.OnFilterItem() {
                            @Override
                            public void onFilter(List<ModelItemProduct> modelItemProducts) {
                                modelItemProductss =modelItemProducts;

                                adapterItemProduct = new AdapterItemProduct(ItemActivity.this,modelItemProductss);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(adapterItemProduct);
                            }
                        });
                        dialog.cancel();
                    }
                },100);
            }
        });
        rd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelItemProductss.clear();
                adapterItemProduct.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        apiServices.FilterItem(id, "priceDown", new ApiServices.OnFilterItem() {
                            @Override
                            public void onFilter(List<ModelItemProduct> modelItemProducts) {
                                modelItemProductss =modelItemProducts;

                                adapterItemProduct = new AdapterItemProduct(ItemActivity.this,modelItemProductss);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(adapterItemProduct);
                            }
                        });
                        dialog.cancel();
                    }
                },100);
            }
        });


        dialog.show();

    }

    private void initializePage() {
        ApiServices apiServices = new ApiServices(ItemActivity.this);
        apiServices.GetItemProduct(id, new ApiServices.OnItemProductReceived() {
            @Override
            public void onItemReceived(List<ModelItemProduct> modelItemProducts) {
                modelItemProductss =modelItemProducts;

                adapterItemProduct = new AdapterItemProduct(ItemActivity.this,modelItemProductss);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapterItemProduct);
            }
        });

    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_item);
        imgBackButton = findViewById(R.id.img_back_second_toolbar);
        txtTitle=findViewById(R.id.txt_title_toolbar_second);
        cardFilter = findViewById(R.id.card_filter);
        apiServices=new ApiServices(ItemActivity.this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
