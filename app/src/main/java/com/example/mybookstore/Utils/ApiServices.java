package com.example.mybookstore.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.DefaultDatabaseErrorHandler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.Models.ModelOff;
import com.google.gson.Gson;

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




    public void registerUser(final String phone, final String email, final String password, final String address, final OnSignUpComplete onSignUpComplete) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("لطفا صبر کنید");
        progressDialog.setMessage("در حال ارسال اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Links.REGISTER_URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int responseStatus = Integer.valueOf(response) ;
                onSignUpComplete.onSignUp(responseStatus);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onSignUpComplete.onSignUp(Put.STATUS_FAILED);
                progressDialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.email,email);
                map.put(Put.address,address);
                map.put(Put.phone,phone);
                map.put(Put.password,password);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(request);


    }

    public void loginUser(final String phone, final String password, final OnLoginComplete onLoginComplete){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("لطفا صبر کنید");
        progressDialog.setMessage("در حال دریافت اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Links.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int loginStatus = Integer.valueOf(response);
                onLoginComplete.onResponse(loginStatus);
                progressDialog.dismiss();
                Log.i("login",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("لطفا صبر کنید");
        progressDialog.setMessage("در حال ازسال اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(1, Links.EDIT_INFORMATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("218")){
                    int editStatus = Integer.valueOf(response);
                    onInformationEdit.onEdit(editStatus);

                }
                else Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطایی رخ داد مجددا امتحان کنید", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("لطفا صبر کنید");
        progressDialog.setMessage("در حال دریافت اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

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
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("informationedit",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void categoryReceive(final OnCategoryReceived categoryReceived){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("لطفا صبر کنید");
        progressDialog.setMessage("در حال دریافت اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

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
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void offReceived(final OnOffReceived onOffReceived){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("لطفا صبر کنید");
        progressDialog.setMessage("در حال دریافت اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(0, Links.GET_OFF, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ModelOff> modelOffs = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    ModelOff modelOff = new ModelOff();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        modelOff.setId(jsonObject.getInt(Put.id));
                        modelOff.setImage(jsonObject.getString(Put.image));
                        modelOff.setLable(jsonObject.getString(Put.lable));
                        modelOff.setPrice(jsonObject.getString(Put.price));
                        modelOff.setTitle(jsonObject.getString(Put.title));
                        modelOff.setVisit(jsonObject.getString(Put.visit));

                        modelOffs.add(modelOff);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onOffReceived.onOffReceived(modelOffs);
                progressDialog.dismiss();

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

    public interface OnSignUpComplete {
        void onSignUp (int responseStatus);
    }

    public interface OnLoginComplete{
        void onResponse(int loginStatus);
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

    public interface  OnOffReceived{
        void onOffReceived(List<ModelOff> modelOffs);
    }

}
