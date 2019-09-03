package com.example.mybookstore.Utils;

import android.content.Context;
import android.database.DefaultDatabaseErrorHandler;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mybookstore.Models.ModelBasket;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.Models.ModelComment;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiServices {

    private Context context;

    public ApiServices(Context context) {
        this.context = context;
    }



    public void registerUser(final String image,final String phone, final String email, final String password, final String address, final OnSignUpComplete onSignUpComplete) {


        StringRequest request = new StringRequest(Request.Method.POST, Links.REGISTER_URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("218")||response.equals("214")||response.equals("0")){
                    int responseStatus = Integer.valueOf(response) ;
                    onSignUpComplete.onSignUp(responseStatus);
                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onSignUpComplete.onSignUp(Put.STATUS_FAILED);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.email,email);
                map.put(Put.address,address);
                map.put(Put.phone,phone);
                map.put(Put.password,password);
                map.put(Put.image,image);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);


    }

    public void loginUser(final String phone, final String password, final OnLoginComplete onLoginComplete){

        StringRequest request = new StringRequest(Request.Method.POST, Links.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.startsWith("2")){
//                    String image = response.substring(1,response.length());
                    String[] split =  response.split(";");
                    String image= split[1].trim();
                    int loginStatus = Integer.valueOf(split[0]);
                    onLoginComplete.onResponse(loginStatus,image);
                }else if (response.startsWith("0")){
                    Toast.makeText(context, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();

                Log.i("login",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("login",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.phone,phone);
                map.put(Put.password,password);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void editInformation(final String phone, final String email, final String address, final String name, final String family, final OnInformationEdit onInformationEdit){


        StringRequest request = new StringRequest(1, Links.EDIT_INFORMATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("218")||response.equals("0")){
                    int editStatus = Integer.valueOf(response);
                    onInformationEdit.onEdit(editStatus);

                }
                else Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد مجددا امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.phone,phone);
                map.put(Put.email,email);
                map.put(Put.address,address);
                map.put(Put.name,name);
                map.put(Put.family,family);

                return map;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void receivePersonalInformation(final String phone, final OnInformationReceived onInformationReceived){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Put.phone,phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(1, Links.GET_PERSONAL_INFORMATION, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("0");
                    String name = jsonObject.getString(Put.name);
                    String email = jsonObject.getString(Put.email);
                    String family = jsonObject.getString(Put.family);
                    String address = jsonObject.getString(Put.address);
                    onInformationReceived.onReceived(name,email,family,address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("informationedit",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void categoryReceive(final OnCategoryReceived categoryReceived){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, Links.GET_CAT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ModelCategory> modelCategories =new ArrayList<>();
                Gson gson = new Gson();
                ModelCategory[] categories = gson.fromJson(response.toString(),ModelCategory[].class);
                for (int i = 0; i < categories.length; i++) {
                    modelCategories.add(categories[i]);
                    categoryReceived.onCatReceived(modelCategories);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void offReceived(final OnOffReceived onOffReceived){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, Links.GET_OFF, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ModelOff_Only_MostVisit> modelOffOnlies = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    ModelOff_Only_MostVisit modelOffOnly = new ModelOff_Only_MostVisit();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        modelOffOnly.setId(jsonObject.getInt(Put.id));
                        modelOffOnly.setImage(jsonObject.getString(Put.image));
                        modelOffOnly.setLable(jsonObject.getString(Put.lable));
                        modelOffOnly.setPrice(jsonObject.getString(Put.price));
                        modelOffOnly.setTitle(jsonObject.getString(Put.title));
                        modelOffOnly.setVisit(jsonObject.getString(Put.visit));
                        modelOffOnly.setOffPrice(jsonObject.getString(Put.offPrice));

                        modelOffOnlies.add(modelOffOnly);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onOffReceived.onOffReceived(modelOffOnlies);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("errorOff",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void onlyReceived(final OnOnlyReceived onOnlyReceived){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, Links.GET_ONLY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ModelOff_Only_MostVisit> modelOnlies = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    ModelOff_Only_MostVisit modelOnly = new ModelOff_Only_MostVisit();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        modelOnly.setId(jsonObject.getInt(Put.id));
                        modelOnly.setImage(jsonObject.getString(Put.image));
                        modelOnly.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelOnly.setPrice(jsonObject.getString(Put.price));
                        modelOnly.setLable(jsonObject.getString(Put.lable));
                        modelOnly.setTitle(jsonObject.getString(Put.title));
                        modelOnly.setVisit(jsonObject.getString(Put.visit));

                        modelOnlies.add(modelOnly);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onOnlyReceived.onOnlyReceived(modelOnlies);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("errorOnly",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void MostvisitReceived(final OnMostVisitReceived onMostVisitReceived){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, Links.GET_MOST_VISIT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ModelOff_Only_MostVisit> modeMostVisits = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    ModelOff_Only_MostVisit modelMostVisit = new ModelOff_Only_MostVisit();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        modelMostVisit.setId(jsonObject.getInt(Put.id));
                        modelMostVisit.setImage(jsonObject.getString(Put.image));
                        modelMostVisit.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelMostVisit.setPrice(jsonObject.getString(Put.price));
                        modelMostVisit.setLable(jsonObject.getString(Put.lable));
                        modelMostVisit.setTitle(jsonObject.getString(Put.title));
                        modelMostVisit.setVisit(jsonObject.getString(Put.visit));

                        modeMostVisits.add(modelMostVisit);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onMostVisitReceived.onMostVisit(modeMostVisits);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("errorOnly",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void MostSoldReceived(final OnMostSoldReceived onMostSoldReceived){



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, Links.GET_MOST_SOLD, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ModelOff_Only_MostVisit> modelMostSolds = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    ModelOff_Only_MostVisit modelMostSold = new ModelOff_Only_MostVisit();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        modelMostSold.setId(jsonObject.getInt(Put.id));
                        modelMostSold.setImage(jsonObject.getString(Put.image));
                        modelMostSold.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelMostSold.setPrice(jsonObject.getString(Put.price));
                        modelMostSold.setLable(jsonObject.getString(Put.lable));
                        modelMostSold.setTitle(jsonObject.getString(Put.title));
                        modelMostSold.setVisit(jsonObject.getString(Put.visit));

                        modelMostSolds.add(modelMostSold);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onMostSoldReceived.onSold(modelMostSolds);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("errorOnly",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void GetProductImage(final String id, final OnImageProductReceived onImageProductReceived ){


        final ArrayList<String> pics  = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(1, Links.GET_IMAGE_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String pic = jsonObject.getString("pics");
                            pics.add(pic);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                onImageProductReceived.onImageReceived(pics);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id,id);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void GetProductInfo(final String id, final OnProductInfoReceived onProductInfoReceived){


        StringRequest stringRequest = new StringRequest(1, Links.GET_DATA_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String image =(jsonObject.getString(Put.image));
                    String price =(jsonObject.getString(Put.price));
                    String title =(jsonObject.getString(Put.title));
                    String visit =(jsonObject.getString(Put.visit));
                    String author =(jsonObject.getString(Put.author));
                    String lable =(jsonObject.getString(Put.lable));
                    String publisher =(jsonObject.getString(Put.publisher));
                    String desc =(jsonObject.getString(Put.desc));
                    String finalrating = String.valueOf((jsonObject.getInt(Put.finalrating)));
//                        int id =(jsonObject.getInt(Put.id));
//                        String lable = (jsonObject.getString(Put.lable));
//                        String offPrice =(jsonObject.getString(Put.offPrice));
//                        String date =(jsonObject.getString(Put.date));
//                        String only =(jsonObject.getString(Put.only));
//                        String num_sold =(jsonObject.getString(Put.num_sold));
                    onProductInfoReceived.onInfoReceived(desc,title,visit,price,finalrating,publisher,author,image,lable);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("errorInfo",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id,id);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void AddToShopCart(final String id, final String title, final String image, final String price, final String phone, final OnShopCartAdd onShopCartAdd){


        StringRequest stringRequest = new StringRequest(1, Links.ADD_TO_SHOP_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("218") || response.equals("0")){
                    int responseStatus = Integer.valueOf(response);
                    onShopCartAdd.onShopCart(responseStatus);

                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.phone,phone);
                map.put(Put.id,id);
                map.put(Put.title,title);
                map.put(Put.image,image);
                map.put(Put.price,price);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void CartReceived(final String phone, final OnCartReceived onCartReceived){


        StringRequest stringRequest = new StringRequest(1, Links.GET_CART, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelBasket> modelBaskets = new ArrayList<>();
                    int totalAllPrice=0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelBasket modelBasket = new ModelBasket();
                        modelBasket.setAllPrice(jsonObject.getString("allprice"));
                        modelBasket.setId(Integer.valueOf(jsonObject.getString("id")));
                        modelBasket.setImage(jsonObject.getString("image"));
                        modelBasket.setTitle(jsonObject.getString("title"));
                        modelBasket.setNumber(jsonObject.getString("number"));
                        modelBasket.setPrice(jsonObject.getString("price"));

                        modelBaskets.add(modelBasket);
                        totalAllPrice+=Integer.parseInt(jsonObject.getString("allprice"));
                    }

                    onCartReceived.onReceived(modelBaskets,totalAllPrice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("errorGetBasket",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.phone,phone);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void DeleteFromCart(final int id ){


        StringRequest stringRequest = new StringRequest(1, Links.LINK_DELETE_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(Put.id, String.valueOf(id));
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void GetCount(final String phone, final OnCountReceived onCountReceived){


        StringRequest stringRequest = new StringRequest(1, Links.GET_COUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onCountReceived.onCount(response);
                Log.i("errorCount",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("errorCount",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.phone,phone);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }

    public void ItemcategoryReceive(final String id, final OnItemCategoryReceived onItemCategoryReceived){



        StringRequest stringRequest = new StringRequest(1, Links.GET_ITEM_CAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<ModelCategory> modelCategories =new ArrayList<>();
                Gson gson = new Gson();
                ModelCategory[] categories = gson.fromJson(response,ModelCategory[].class);
                for (int i = 0; i < categories.length; i++) {
                    modelCategories.add(categories[i]);
                    onItemCategoryReceived.onItemReceived(modelCategories);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id,id);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void GetItemProduct(final String id , final OnItemProductReceived onItemProductReceived){
//        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
//        progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar

        StringRequest stringRequest = new StringRequest(1, Links.GET_ITEM_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelItemProduct> modelItemProduct = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelItemProduct modelItemProduct1 = new ModelItemProduct();

                        modelItemProduct1.setDesc(jsonObject.getString(Put.desc));
                        modelItemProduct1.setId(jsonObject.getInt(Put.id));
                        modelItemProduct1.setImage(jsonObject.getString(Put.image));
                        modelItemProduct1.setLable(jsonObject.getString(Put.lable));
                        modelItemProduct1.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelItemProduct1.setTitle(jsonObject.getString(Put.title));
                        modelItemProduct1.setPrice(jsonObject.getString(Put.price));
                        modelItemProduct1.setVisit(jsonObject.getString(Put.visit));
                        modelItemProduct1.setAuthor(jsonObject.getString(Put.author));
                        modelItemProduct1.setPublisher(jsonObject.getString(Put.publisher));


                        modelItemProduct.add(modelItemProduct1);
                    }
                    onItemProductReceived.onItemReceived(modelItemProduct);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id,id);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void SearchProduct(final String search,final OnSearchComplete onSearchComplete){

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, Links.SEARCH + search, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                onSearchComplete.OnComplete(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void GetComment (final String id,final OnCommentReceived onCommentReceived){

        StringRequest stringRequest = new StringRequest(1, Links.GET_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                List<ModelComment> modelComments = new ArrayList<>();
//                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
//                Log.i("idi",response);
//                Gson gson = new Gson();
//                ModelComment[] comments = gson.fromJson(response,ModelComment[].class);
//                for (int i = 0; i < comments.length; i++) {
//                    modelComments.add(comments[i]);
//                    onCommentReceived.onComment(modelComments);
//                }
                Log.i("rresponseComment",response);
                List<ModelComment> modelComment = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ModelComment comment = new ModelComment();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        comment.setComment(jsonObject.getString(Put.comment));
                        comment.setUser(jsonObject.getString(Put.user));
                        comment.setImage(jsonObject.getString(Put.image));
                        comment.setNegative(jsonObject.getString(Put.negative));
                        comment.setPositive(jsonObject.getString(Put.positive));
                        comment.setRating(jsonObject.getLong(Put.rating));

                        modelComment.add(comment);

                    }
                    onCommentReceived.onComment(modelComment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("rresponseComment",error.toString());

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id,id);
                return map;
            }
        }
        ;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void SendComment(final String idProduct, final String phone, final String image, final String comment, final String positive, final String negative, final float rating,
                            final OnCommentSend onCommentSend){

        StringRequest stringRequest = new StringRequest(1,Links.SEND_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ressss",response);
                if (response.equals("218")){
                    onCommentSend.onSend(Integer.valueOf(response));
                }else Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("errrrrr",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id,idProduct);
                map.put(Put.image,image);
                map.put(Put.phone,phone);
                map.put(Put.negative,negative);
                map.put(Put.positive,positive);
                map.put(Put.comment,comment);
                map.put(Put.rating, String.valueOf(rating));
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void FilterItem(final String id, final String param , final OnFilterItem onFilterItem){

        StringRequest stringRequest = new StringRequest(1, Links.FILTER_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelItemProduct> modelItemProduct = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelItemProduct modelItemProduct1 = new ModelItemProduct();

                        modelItemProduct1.setDesc(jsonObject.getString(Put.desc));
                        modelItemProduct1.setId(jsonObject.getInt(Put.id));
                        modelItemProduct1.setImage(jsonObject.getString(Put.image));
                        modelItemProduct1.setLable(jsonObject.getString(Put.lable));
                        modelItemProduct1.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelItemProduct1.setTitle(jsonObject.getString(Put.title));
                        modelItemProduct1.setPrice(jsonObject.getString(Put.price));
                        modelItemProduct1.setVisit(jsonObject.getString(Put.visit));
                        modelItemProduct1.setAuthor(jsonObject.getString(Put.author));
                        modelItemProduct1.setPublisher(jsonObject.getString(Put.publisher));

                        modelItemProduct.add(modelItemProduct1);
                    }
                    onFilterItem.onFilter(modelItemProduct);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("idcat",id);
                map.put(Put.params,param);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }




    public interface OnSignUpComplete {
        void onSignUp (int responseStatus);
    }
    public interface OnLoginComplete{
        void onResponse(int loginStatus,String image);
    }
    public interface OnInformationEdit{
        void onEdit(int editStatus);
    }
    public interface OnInformationReceived{
        void onReceived(String name,String family,String email,String address);
    }
    public interface OnCategoryReceived{
        void onCatReceived(List<ModelCategory> modelCategories);
    }
    public interface OnOffReceived{
        void onOffReceived(List<ModelOff_Only_MostVisit> modelOffOnlies);
    }
    public interface OnOnlyReceived{
        void onOnlyReceived(List<ModelOff_Only_MostVisit> modelOnlies);
    }
    public interface OnMostVisitReceived{
        void onMostVisit(List<ModelOff_Only_MostVisit> modelMostVisit);
    }
    public interface OnMostSoldReceived{
        void onSold(List<ModelOff_Only_MostVisit> modelSold);
    }
    public interface OnImageProductReceived{
        void onImageReceived(ArrayList<String> pics);
    }
    public interface OnProductInfoReceived{
        void onInfoReceived(String desc,String title,String visit,String price,
                            String finalrating,String publisher,String author,String image,String lable) ;
    }
    public interface OnShopCartAdd{
        void onShopCart(int responseStatus);
    }
    public interface OnCartReceived{
        void onReceived(List<ModelBasket> modelBaskets,int totalPrice);
    }
    public interface OnCountReceived{
        void onCount(String count);
    }
    public interface OnItemCategoryReceived{
        void onItemReceived(List<ModelCategory> modelCategories);
    }
    public interface OnItemProductReceived{
        void onItemReceived(List<ModelItemProduct> modelItemProducts);
    }
    public interface OnSearchComplete{
        void OnComplete(JSONObject jsonObject);
    }
    public interface OnCommentReceived{
        void onComment(List<ModelComment> modelComments);
    }
    public interface OnCommentSend{
        void onSend(int responsStatus);
    }
    public interface OnFilterItem{
        void onFilter(List<ModelItemProduct> modelItemProducts);
    }




}
