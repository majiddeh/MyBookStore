package com.example.mybookstore.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Adapters.AdapterSearch;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.Models.ModelSearch;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText edSearch;
    RecyclerView recyclerViewSearch;
    AdapterSearch adapterSearch;
    List<ModelSearch> modelSearches = new ArrayList<>();
    String id, image, title, price, description, offPrice, lable, visit;
    boolean array = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findwiews();
        editor();

    }

    private void findwiews() {
        edSearch = findViewById(R.id.edsearch);
        recyclerViewSearch = findViewById(R.id.recycler_search);
        adapterSearch = new AdapterSearch(SearchActivity.this, modelSearches);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSearch.setAdapter(adapterSearch);
        recyclerViewSearch.hasFixedSize();
    }

    private void editor() {
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                final ApiServices apiServices = new ApiServices(getApplicationContext());
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    array = modelSearches.isEmpty();
                    if (array) {
                        apiServices.SearchProduct(edSearch.getText().toString().trim(), new ApiServices.OnSearchComplete() {
                            @Override
                            public void OnComplete(JSONObject jsonObject) {
                                try {
                                    JSONObject object1 = new JSONObject(jsonObject.toString());
                                    JSONArray array = object1.getJSONArray("records");

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        id = object.getString(Put.id);
                                        image = object.getString(Put.image);
                                        title = object.getString(Put.title);
                                        price = object.getString(Put.price);
                                        lable = object.getString(Put.lable);
                                        offPrice = String.valueOf(object.getInt(Put.offPrice));
                                        visit = object.getString(Put.visit);
                                        description = object.getString(Put.desc);

                                        modelSearches.add(new ModelSearch(Integer.parseInt(id), image, title, visit, price, lable, offPrice, description));
                                        adapterSearch.notifyDataSetChanged();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        edSearch.setText("");
                    } else {
                        modelSearches.clear();
                        adapterSearch.notifyDataSetChanged();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                apiServices.SearchProduct(edSearch.getText().toString().trim(), new ApiServices.OnSearchComplete() {
                                    @Override
                                    public void OnComplete(JSONObject jsonObject) {
                                        try {
                                            JSONObject object1 = new JSONObject(jsonObject.toString());
                                            JSONArray array = object1.getJSONArray("records");

                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject(i);
                                                id = object.getString(Put.id);
                                                image = object.getString(Put.image);
                                                title = object.getString(Put.title);
                                                lable = object.getString(Put.lable);
                                                price = object.getString(Put.price);
                                                offPrice = String.valueOf(object.getInt(Put.offPrice));
                                                visit = object.getString(Put.visit);
                                                description = object.getString(Put.desc);

                                                modelSearches.add(new ModelSearch(Integer.parseInt(id), image, title, visit, price, lable, offPrice, description));
                                                adapterSearch.notifyDataSetChanged();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                edSearch.setText("");
                            }
                        }, 100);
                    }

                }
                return false;
            }
        });
    }

//    private void editor(){
//        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                if (actionId == EditorInfo.IME_ACTION_SEARCH){
//                    array=modelSearches.isEmpty();
//                    if (array){
//                        getSearch(edSearch.getText().toString().trim());
//                        edSearch.setText("");
//                    }else {
//                        modelSearches.clear();
//                        adapterSearch.notifyDataSetChanged();
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getSearch(edSearch.getText().toString().trim());
//                                edSearch.setText("");
//                            }
//                        },100);
//                    }
//
//                }
//                return false;
//            }
//        });
//    }
//
//    private void getSearch(final String search){
//
////        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
////        progressDialog.show();
////        progressDialog.setCancelable(false);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, Links.SEARCH + search, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(SearchActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//
//                try {
//                    JSONObject jsonObject= new JSONObject(response.toString());
//                    JSONArray array = jsonObject.getJSONArray("records");
//
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject object = array.getJSONObject(i);
//                        id=object.getString(Put.id);
//                        image=object.getString(Put.image);
//                        title=object.getString(Put.title);
//                        price=object.getString(Put.price);
//
//                        modelSearches.add((new ModelItemProduct(Integer.parseInt(id), image, title, visit, price,lable,offPrice,description)));
//                        adapterSearch.notifyDataSetChanged();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
//
//
//    }

}
