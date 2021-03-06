package com.example.mybookstore.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mybookstore.Activities.ChangePassActivity;
import com.example.mybookstore.Activities.EditInformation;
import com.example.mybookstore.Activities.LoginActivity;
import com.example.mybookstore.Activities.MainActivity;
import com.example.mybookstore.Activities.ProfileActivity;
import com.example.mybookstore.Activities.VerfyCodeActivity;
import com.example.mybookstore.Models.ModelAddress;
import com.example.mybookstore.Models.ModelBanner;
import com.example.mybookstore.Models.ModelBasket;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.Models.ModelComment;
import com.example.mybookstore.Models.ModelFav;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.Models.ModelLikes;
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.Models.ModelSlider;
import com.example.mybookstore.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.pnikosis.materialishprogress.ProgressWheel;

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

    public void registeWhitSMS(final String username, final String password, final ProgressWheel progressWheel) {

        progressWheel.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, Links.REGISTER_URL_HA , new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                progressWheel.setVisibility(View.GONE);
                Log.i("registerapi",response.toString());
                if (response.equals("218")||response.equals("214")||response.equals("0")||response.equals("211")){
                    if (response.equals("218")){
                                        Intent intent = new Intent(context, VerfyCodeActivity.class);
                                        intent.putExtra(Put.username,username);
                                        intent.putExtra("reg","reg");
                                        context.startActivity(intent);
                                        ((Activity)context).finish();
                    }else if (response.equals("214")){
                        Toast.makeText(context, "چنین کاربری قبلا ثبت نام کرده است", Toast.LENGTH_SHORT).show();
                    }else if (response.equals("0")){
                        Toast.makeText(context, "خطا!! لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
                    }else if (response.equals("211")){
                        Toast.makeText(context, "نام کاربری را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressWheel.setVisibility(View.GONE);
                Log.i("registererror",error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.username,username);
                map.put(Put.password,password);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);


    }

    public void VerfyCode(final String username, final String code,final String reg, final ProgressWheel progressWheel) {

        String url = "";
        if (reg.equals("reg") || reg.equals("notverficated")){
             url = Links.CHECK_CODE_HA;
        }else if (reg.equals("change")){
             url = Links.CHECK_CODE_HA_FORGET;
        }

        progressWheel.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                progressWheel.setVisibility(View.GONE);
                Log.i("registerapi",response.toString());

                if (response.length()==32){
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dilaog_default);
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false);
                        TextView tvTitle,tvText,tvNegativ,tvPositive;
                        tvNegativ=dialog.findViewById(R.id.tv_dialog_negative);
                        tvPositive=dialog.findViewById(R.id.tv_dialog_positive);
                        tvText=dialog.findViewById(R.id.tv_dialog_text);
                        tvTitle=dialog.findViewById(R.id.tv_dialog_title);
                        tvNegativ.setText("خیر بعدا انجام میدم");
                        tvPositive.setText("بله");

                        tvNegativ.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(context);
                                        userSharedPrefrences.saveUserLoginInfoHA(username,response);
                                        Intent intent = new Intent(context, MainActivity.class);
                                        intent.putExtra(Put.username,username);
                                        context.startActivity(intent);
                                        ((Activity)context).finish();
                                    }
                                },500);

                            }
                        });

                        tvPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(context);
                                        userSharedPrefrences.saveUserLoginInfoHA(username,response);
                                        Intent intent = new Intent(context, EditInformation.class);
                                        intent.putExtra(Put.newuser,Put.newuser);
                                        context.startActivity(intent);
                                        ((Activity)context).finish();
                                    }
                                },500);

                            }
                        });

                        tvTitle.setText("ثبت نام با موفقیت انجام شد");
                        tvText.setText("آیا مایل به تکمیل ثبت نام و ثبت آدرس و اطلاعات فردی هستید؟");

//                } else if (response.equals("213")){
//                        Toast.makeText(context, "کد نا معتبر است", Toast.LENGTH_SHORT).show();
//                    }else if (response.equals("0")){
//                        Toast.makeText(context, "لطفا ورودی هارا کنترل کنید", Toast.LENGTH_SHORT).show();
//                    }else if (response.equals("315")){
//                        Toast.makeText(context, "چنین کاربری وجود ندارد", Toast.LENGTH_SHORT).show();


                    }else if(response.equals("213")){
                    Toast.makeText(context, "کد نامعتبر", Toast.LENGTH_SHORT).show();

                }else if (response.equals("218")){
                    Intent intent = new Intent(context, ChangePassActivity.class);
                    intent.putExtra(Put.username,username);
                    intent.putExtra("changepass","forget");
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressWheel.setVisibility(View.GONE);
                Log.i("registererror",error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.username,username);
                map.put(Put.code,code);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);


    }

    public void loginUserHA(final String username, final String password, final ProgressWheel progressWheel){
        progressWheel.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, Links.LOGIN_URL_HA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);
                if (response.equals("0")){
                    Toast.makeText(context, "رمز عبور یا نام کربری اشتباه است", Toast.LENGTH_SHORT).show();
                }else if (response.equals("01")){
                    Toast.makeText(context, "نام کاربری و رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();
                }else if (response.equals("209")){
                    Toast.makeText(context, "فعالسازی انجام نشده", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, VerfyCodeActivity.class);
                    intent.putExtra("reg","notverficated");
                    intent.putExtra(Put.username,username);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else if (response.length()==32){
                    UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(context);
                    userSharedPrefrences.saveUserLoginInfoHA(username,response);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Put.username,username);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
                Log.i("loginapi",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("loginapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.username,username);
                map.put(Put.password,password);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void ChangePass(final String username,final String token, final String oldPass, final String newPass, final String rePass, final ProgressWheel progressWheel){
        progressWheel.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, Links.CHANGE_PASS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);
                if (response.equals("354")){
                    Toast.makeText(context, "رمز قبلی همخوانی ندارد", Toast.LENGTH_SHORT).show();
                }else if (response.equals("370")){
                    Toast.makeText(context, "رمز عبور همخوانی ندارد", Toast.LENGTH_SHORT).show();
                }else if (response.equals("210")) {
                    Toast.makeText(context, "رمز باید بیش از 4 کاراکتر باشد", Toast.LENGTH_SHORT).show();
                }else if (response.equals("218")){
                    Toast.makeText(context, "رمز عبور با موفقیت تغییر کرد", Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
                Log.i("loginapi",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("loginapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.username,username);
                map.put("oldpass",oldPass);
                map.put("newpass",newPass);
                map.put("repass",rePass);
                map.put(Put.token,token);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void SetPass(final  String username ,final String pass,final String repass, final ProgressWheel progressWheel){
        progressWheel.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, Links.SET_PASS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);
                if (response.equals("370")){
                    Toast.makeText(context, "رمز عبور همخوانی ندارد", Toast.LENGTH_SHORT).show();
                }else if (response.equals("210")) {
                    Toast.makeText(context, "رمز باید بیش از 4 کاراکتر باشد", Toast.LENGTH_SHORT).show();
                }else if (response.equals("218")){
                    Toast.makeText(context, "رمز عبور با موفقیت تغییر کرد", Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(context,LoginActivity.class);
                            intent.putExtra(Put.username,username);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                    },1000);

                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
                Log.i("loginapi",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("loginapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.username,username);
                map.put(Put.password,pass);
                map.put(Put.repass,repass);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void ForgetPass(final String username, final ProgressWheel progressWheel){
        progressWheel.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, Links.FORGOT_PASS, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);
                if (response.equals("218")){
                    Intent intent = new Intent(context,VerfyCodeActivity.class);
                    intent.putExtra(Put.username,username);
                    intent.putExtra("reg","change");
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else if (response.equals("315")){
                    Toast.makeText(context, "چنین کاربری وجود ندارد...", Toast.LENGTH_SHORT).show();
                }else if (response.equals("211")){
                    Toast.makeText(context, "لطفا نام کاربری را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();



                Log.i("apiii",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("apiii",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.username,username);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void receivePersonalInformation(final String username, final OnInformationReceived onInformationReceived){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Put.username,username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(1, Links.GET_PERSONAL_INFORMATION, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("infoapi",response.toString());
                try {
                    JSONObject jsonObject = response.getJSONObject("0");
                    String name = jsonObject.getString(Put.name);
                    String family = jsonObject.getString(Put.family);
                    onInformationReceived.onReceived(name,family);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("infoapierror",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void AddAddress(final String token, final String name, final String family, final String postalcode, final String phone,
                           final String mobile, final String idcity,final String idCityCap, final String address, final OnAddressAdd onAddressAdd){


        StringRequest request = new StringRequest(1, Links.ADD_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onAddressAdd.onAddress(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد مجددا امتحان کنید", Toast.LENGTH_SHORT).show();
                Log.i("editapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.token,token);
                map.put(Put.name,name);
                map.put(Put.family,family);
                map.put(Put.postalcode,postalcode);
                map.put(Put.phone,phone);
                map.put(Put.mobile,mobile);
                map.put("idcity",idcity);
                map.put("idcitycap",idCityCap);
                map.put(Put.address,address);

                return map;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void GetAddress(final String token, final OnAddressReceived onAddressReceived){


        StringRequest request = new StringRequest(1, Links.GET_ADDRESS,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelAddress> modelAddresses = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        ModelAddress modelAddress = new ModelAddress();
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            modelAddress.setIdAddress(jsonObject.getInt("idaddress"));
                            modelAddress.setName(jsonObject.getString(Put.name));
                            modelAddress.setFamily(jsonObject.getString(Put.family));
                            modelAddress.setPhone(jsonObject.getString(Put.phone));
                            modelAddress.setMobile(jsonObject.getString(Put.mobile));
                            modelAddress.setCity(jsonObject.getString("city"));
                            modelAddress.setCityCap(jsonObject.getString("citycap"));
                            modelAddress.setPostalcode(jsonObject.getString("postalcode"));
                            modelAddress.setIduser(jsonObject.getString("iduser"));
                            modelAddress.setAddress(jsonObject.getString("address"));
                            modelAddress.setIdcity(jsonObject.getString("idcity"));
                            modelAddress.setIdcityCap(jsonObject.getString("idcitycap"));

                            modelAddresses.add(modelAddress);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    onAddressReceived.onReceived(modelAddresses);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد مجددا امتحان کنید", Toast.LENGTH_SHORT).show();
                Log.i("editapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.token,token);
                return map;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void DelAddress (final String idAddress, final OnAddressDeleted onAddressDeleted){


        StringRequest request = new StringRequest(1, Links.DEL_ADDRESS,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("218")){
                    onAddressDeleted.onDeleted(true);
                }else {
                    onAddressDeleted.onDeleted(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد مجددا امتحان کنید", Toast.LENGTH_SHORT).show();
                Log.i("editapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put("idaddress",idAddress);
                return map;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void editInformation(final String username,final String name, final String family){


        StringRequest request = new StringRequest(1, Links.EDIT_INFORMATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("218")||response.equals("0")){

                }
                else Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                Log.i("editapi",response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد مجددا امتحان کنید", Toast.LENGTH_SHORT).show();
                Log.i("editapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.username,username);
                map.put(Put.name,name);
                map.put(Put.family,family);
                return map;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

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

    public void categoryReceiveViewPager(final OnCatStringReceived onCatStringReceived){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, Links.GET_CAT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> strings =new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        strings.add(jsonObject.getString("title_category"));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                onCatStringReceived.onCatReceived(strings);



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

    public void offReceived(String link,final OnOffReceived onOffReceived){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, link, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ModelOff_Only_MostVisit> modelOffOnlies = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    ModelOff_Only_MostVisit modelOffOnly = new ModelOff_Only_MostVisit();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        modelOffOnly.setId(jsonObject.getInt(Put.id));
                        modelOffOnly.setImage(jsonObject.getString(Put.image));
                        modelOffOnly.setLable(jsonObject.getString(Put.label));
                        modelOffOnly.setPrice(jsonObject.getString(Put.price));
                        modelOffOnly.setTitle(jsonObject.getString(Put.title));
                        modelOffOnly.setCat(jsonObject.getString(Put.cat));
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
                Log.i("apiii",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void onlyReceived(String link,final OnOnlyReceived onOnlyReceived){


        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0,link , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("apiii",response.toString());
                List<ModelOff_Only_MostVisit> modelOnlies = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    ModelOff_Only_MostVisit modelOnly = new ModelOff_Only_MostVisit();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        modelOnly.setId(jsonObject.getInt(Put.id));
                        modelOnly.setImage(jsonObject.getString(Put.image));
                        modelOnly.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelOnly.setPrice(jsonObject.getString(Put.price));
                        modelOnly.setLable(jsonObject.getString(Put.label));
                        modelOnly.setTitle(jsonObject.getString(Put.title));
                        modelOnly.setVisit(jsonObject.getString(Put.visit));
                        modelOnly.setCat(jsonObject.getString(Put.cat));

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
                Log.i("apiii",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void MostvisitReceived(String link,final OnMostVisitReceived onMostVisitReceived){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0,link, null, new Response.Listener<JSONArray>() {
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
                        modelMostVisit.setLable(jsonObject.getString(Put.label));
                        modelMostVisit.setTitle(jsonObject.getString(Put.title));
                        modelMostVisit.setCat(jsonObject.getString(Put.cat));
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
                Log.i("apiii",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void MostSoldReceived(String link,final OnMostSoldReceived onMostSoldReceived){



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0,link, null, new Response.Listener<JSONArray>() {
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
                        modelMostSold.setLable(jsonObject.getString(Put.label));
                        modelMostSold.setTitle(jsonObject.getString(Put.title));
                        modelMostSold.setVisit(jsonObject.getString(Put.visit));
                        modelMostSold.setCat(jsonObject.getString(Put.cat));

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
                Log.i("apiii",error.toString());

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
                    String lable =(jsonObject.getString(Put.label));
                    String publisher =(jsonObject.getString(Put.publisher));
                    String desc =(jsonObject.getString(Put.desc));
                    String finalrating = String.valueOf((jsonObject.getInt(Put.finalrating)));
                    String cat = jsonObject.getString(Put.cat);
//                        int id =(jsonObject.getInt(Put.id));
//                        String label = (jsonObject.getString(Put.label));
                        String offPrice =(jsonObject.getString(Put.offPrice));
//                        String date =(jsonObject.getString(Put.date));
//                        String only =(jsonObject.getString(Put.only));
//                        String num_sold =(jsonObject.getString(Put.num_sold));
                    onProductInfoReceived.onInfoReceived(desc,title,visit,price,finalrating,publisher,author,image,lable,cat,offPrice);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("apiii",error.toString());

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

    public void AddToShopCart(final String id, final String title, final String image, final String price, final String token, final OnShopCartAdd onShopCartAdd){


        StringRequest stringRequest = new StringRequest(1, Links.ADD_TO_SHOP_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("218") || response.equals("0")){
                    int responseStatus = Integer.valueOf(response);
                    onShopCartAdd.onShopCart(responseStatus);

                }else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    Log.i("apiii",response.toString());
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
                map.put(Put.token,token);
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

    public void CartReceived(final ProgressWheel progressWheel, final String token, final OnCartReceived onCartReceived){

        progressWheel.setVisibility(View.VISIBLE);
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

            progressWheel.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("apiii",error.toString());
                progressWheel.setVisibility(View.GONE);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.token,token);
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

    public void GetCount(final String token, final OnCountReceived onCountReceived){


        StringRequest stringRequest = new StringRequest(1, Links.GET_COUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onCountReceived.onCount(response);
                Log.i("apiii",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("apiii",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.token,token);
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
                        modelItemProduct1.setLable(jsonObject.getString(Put.label));
                        modelItemProduct1.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelItemProduct1.setTitle(jsonObject.getString(Put.title));
                        modelItemProduct1.setPrice(jsonObject.getString(Put.price));
                        modelItemProduct1.setVisit(jsonObject.getString(Put.visit));
                        modelItemProduct1.setCat(jsonObject.getString(Put.cat));
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
                Log.i("apiii",response);
                List<ModelComment> modelComment = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ModelComment comment = new ModelComment();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        comment.setComment(jsonObject.getString(Put.comment));
                        comment.setUser(jsonObject.getString(Put.user));
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
                Log.i("apiii",error.toString());

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

    public void SendComment(final String idProduct, final String phone, final String comment, final String positive, final String negative, final float rating,
                            final OnCommentSend onCommentSend){

        StringRequest stringRequest = new StringRequest(1,Links.SEND_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("apiii",response);
                if (response.equals("218")){
                    onCommentSend.onSend(Integer.valueOf(response));
                }else Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("apiii",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id,idProduct);
                map.put(Put.username,phone);
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
                        modelItemProduct1.setLable(jsonObject.getString(Put.label));
                        modelItemProduct1.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelItemProduct1.setTitle(jsonObject.getString(Put.title));
                        modelItemProduct1.setCat(jsonObject.getString(Put.cat));
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

    public void GetBanners(final OnBannerReceived onBannerReceived){

        StringRequest stringRequest = new StringRequest(0, Links.GET_BANNER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelBanner> modelBanners = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelBanner modelBanner = new ModelBanner();

                        modelBanner.setId(jsonObject.getInt(Put.id));
                        modelBanner.setImage(jsonObject.getString(Put.image));

                        modelBanners.add(modelBanner);
                    }
                    onBannerReceived.onBanner(modelBanners);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void GetBannerBig(final OnBannerReceived onBannerReceived){

        StringRequest stringRequest = new StringRequest(0, Links.GET_BANNER_BIG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelBanner> modelBanners = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelBanner modelBanner = new ModelBanner();

                        modelBanner.setId(jsonObject.getInt(Put.id));
                        modelBanner.setImage(jsonObject.getString(Put.image));

                        modelBanners.add(modelBanner);
                    }
                    onBannerReceived.onBanner(modelBanners);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void GetItemBanner(final String id , final OnItemBannerReceived onItemBannerReceived){
//        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
//        progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar

        StringRequest stringRequest = new StringRequest(1, Links.GET_BANNER_ITEM, new Response.Listener<String>() {
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
                        modelItemProduct1.setLable(jsonObject.getString(Put.label));
                        modelItemProduct1.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelItemProduct1.setTitle(jsonObject.getString(Put.title));
                        modelItemProduct1.setPrice(jsonObject.getString(Put.price));
                        modelItemProduct1.setCat(jsonObject.getString(Put.cat));
                        modelItemProduct1.setVisit(jsonObject.getString(Put.visit));
                        modelItemProduct1.setAuthor(jsonObject.getString(Put.author));
                        modelItemProduct1.setPublisher(jsonObject.getString(Put.publisher));


                        modelItemProduct.add(modelItemProduct1);
                    }
                    onItemBannerReceived.onItemReceived(modelItemProduct);
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

    public void GetLikeProduct(final String id,final String cat, final OnLikeReceived onLikeReceived){

        StringRequest stringRequest = new StringRequest(1, Links.GET_LIKES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("apiii",response.toString());
                List<ModelLikes> modelLikes = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelLikes modelLikes1 = new ModelLikes();

                        modelLikes1.setId(jsonObject.getInt(Put.id));
                        modelLikes1.setImage(jsonObject.getString(Put.image));
                        modelLikes1.setLable(jsonObject.getString(Put.label));
                        modelLikes1.setTitle(jsonObject.getString(Put.title));
                        modelLikes1.setPrice(jsonObject.getString(Put.price));
                        modelLikes1.setVisit(jsonObject.getString(Put.visit));
                        modelLikes1.setCat(jsonObject.getString(Put.cat));


                        modelLikes.add(modelLikes1);
                    }
                    onLikeReceived.onLike(modelLikes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    List<ModelOff_Only_MostVisit> modelOffOnlyMostVisits =new ArrayList<>();
//                    Gson gson = new Gson();
//                    ModelOff_Only_MostVisit[] modelOffOnlyMostVisits1 = gson.fromJson(jsonArray.toString(),ModelOff_Only_MostVisit[].class);
//                    for (int i = 0; i < modelOffOnlyMostVisits1.length; i++) {
//                        modelOffOnlyMostVisits.add(modelOffOnlyMostVisits1[i]);
//                        onLikeReceived.onLike(modelOffOnlyMostVisits);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("apiiilike",error.toString());



            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.cat,cat);
                map.put(Put.id,id);
                return map;
            }
        }
        ;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void AddToFav(final String idproduct,final String username, final OnFavAdd onFavAdd){


        StringRequest stringRequest = new StringRequest(1, Links.ADD_TO_FAV, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("218") || response.equals("213")){
                    int responseStatus = Integer.valueOf(response);
                    onFavAdd.onFav(responseStatus);

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
                map.put(Put.username,username);
                map.put(Put.id,idproduct);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void FavReceived(final String phone, final OnFavtReceived onFavtReceived){


        StringRequest stringRequest = new StringRequest(1, Links.GET_FAV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("apiii",response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelFav> modelFavs = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelFav modelFav = new ModelFav();
                        modelFav.setId(Integer.valueOf(jsonObject.getString("id")));
                        modelFav.setImage(jsonObject.getString("image"));
                        modelFav.setTitle(jsonObject.getString("title"));
                        modelFav.setLable(jsonObject.getString(Put.label));
                        modelFav.setPrice(jsonObject.getString("price"));
                        modelFav.setOffPrice(jsonObject.getString(Put.offPrice));
                        modelFav.setVisit(jsonObject.getString(Put.visit));
                        modelFav.setAuthor(jsonObject.getString(Put.author));
                        modelFav.setPublisher(jsonObject.getString(Put.publisher));
                        modelFav.setDesc(jsonObject.getString("description"));
                        modelFav.setCat(jsonObject.getString(Put.cat));

                        modelFavs.add(modelFav);
                    }

                    onFavtReceived.onReceived(modelFavs);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                Log.i("apiii",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.username,phone);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void CheckFav(final String idproduct,final String phone, final OnFavCheck onFavCheck){


        StringRequest stringRequest = new StringRequest(1, Links.CHECK_FAV, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("218") || response.equals("0")){
                    int responseStatus = Integer.valueOf(response);
                    onFavCheck.onCheck(responseStatus);

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
                map.put(Put.username,phone);
                map.put(Put.id,idproduct);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void GetSliders(final OnSliderReceived onSliderReceived){

        StringRequest stringRequest = new StringRequest(0, Links.GET_SLIDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ModelSlider> modelSliders = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ModelSlider modelSlider = new ModelSlider();

                        modelSlider.setId(jsonObject.getInt(Put.id));
                        modelSlider.setImage(jsonObject.getString(Put.image));
                        modelSlider.setName(jsonObject.getString(Put.name));

                        modelSliders.add(modelSlider);
                    }
                    onSliderReceived.onSlider(modelSliders);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

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
        void onReceived(String name,String family);
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
                            String finalrating,String publisher,String author,String image,String lable,String cat,String offPrice) ;
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
    public interface OnBannerReceived{
        void onBanner(List<ModelBanner> modelBanners);
    }
    public interface OnItemBannerReceived{
        void onItemReceived(List<ModelItemProduct> modelItemProducts);
    }
    public interface OnLikeReceived{
        void onLike(List<ModelLikes> modelLikes);
    }
    public interface OnForgetPass{
        void OnForget(int responseStatus);
    }
    public interface OnCatStringReceived{
        void onCatReceived(List<String> strings);
    }
    public interface OnFavAdd{
        void onFav(int responseStatus);
    }
    public interface OnFavtReceived{
        void onReceived(List<ModelFav> modelFavs);
    }
    public interface OnFavCheck{
        void onCheck(int responseStatus);
    }
    public interface OnSliderReceived{
        void onSlider(List<ModelSlider> modelSliders);
    }
    public interface OnAddressAdd{
        void onAddress(String response);
    }
    public interface OnAddressReceived{
        void onReceived(List<ModelAddress> modelAddresses);
    }
    public interface OnAddressDeleted{
        void onDeleted(boolean succeed);
    }












    public void registerUser(final String image,final String phone, final String email, final String password, final String address, final OnSignUpComplete onSignUpComplete) {


        StringRequest request = new StringRequest(Request.Method.POST, Links.REGISTER_URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("registerapi",response.toString());
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
                Log.i("registererror",error.toString());

                onSignUpComplete.onSignUp(Put.STATUS_FAILED);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.email,email);
                map.put(Put.address,address);
                map.put(Put.username,phone);
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

                Log.i("loginapi",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("loginapierror",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put(Put.username,phone);
                map.put(Put.password,password);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }



}
