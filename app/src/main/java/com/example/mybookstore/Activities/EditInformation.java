package com.example.mybookstore.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mybookstore.Adapters.AdapterAddress;
import com.example.mybookstore.Models.ModelAddress;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.MySingleton;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditInformation extends AppCompatActivity {
    private EditText edName,edFamily,edAddress;
    private ImageView imgback;
    private TextView txtTitle;
    private Button btnEdit,btnAddAddress;
    private String username,token;
    private ApiServices apiServices = new ApiServices(EditInformation.this);
    private RecyclerView recyclerView;
    private AdapterAddress adapterAddress;
    List<String> cityCap = new ArrayList<>();
    List<String> city = new ArrayList<>();
    List<String> arrayIDcityCap = new ArrayList<>();
    List<String> arrayIDcity = new ArrayList<>();
    String idcityCap = null;
    String idcity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        findViews();
        receiveInformation();
        onClicks();
    }

    private void receiveInformation() {
        apiServices.receivePersonalInformation(username, new ApiServices.OnInformationReceived() {
            @Override
            public void onReceived(String name, String family ) {
                edFamily.setText(family);
                edName.setText(name);
            }
        });

        apiServices.GetAddress(token, new ApiServices.OnAddressReceived() {
            @Override
            public void onReceived(List<ModelAddress> modelAddresses) {
                adapterAddress=new AdapterAddress(EditInformation.this,modelAddresses);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerView.hasFixedSize();
                recyclerView.setAdapter(adapterAddress);
            }
        });

    }

    private void onClicks() {

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (getIntent().getStringExtra(Put.newuser)!=null){
//                    apiServices.editInformation(username,edName.getText().toString().trim(), edFamily.getText().toString().trim(), new ApiServices.OnInformationEdit() {
//                                @Override
//                                public void onEdit(int editStatus) {
//                                    if (editStatus==Put.STATUS_SUCCESS){
//                                        Toast.makeText(EditInformation.this, "اطلاعات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(EditInformation.this, MainActivity.class);
//                            intent.putExtra(Put.username,username);
//                            startActivity(intent);
//                            finish();
//                        }
//                    },1000);
//
//                }else{
//                    apiServices.editInformation(username,edName.getText().toString().trim(), edFamily.getText().toString().trim(), new ApiServices.OnInformationEdit() {
//                                @Override
//                                public void onEdit(int editStatus) {
//                                    if (editStatus==Put.STATUS_SUCCESS){
//                                        Toast.makeText(EditInformation.this, "اطلاعات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
            }
        });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(EditInformation.this);
                dialog.setContentView(R.layout.dialog_address);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                final EditText etName,etFamiy,etPostalCode,etPhone,etMobile,etAddress;
                TextView tvNegativ,tvPositive;
                final Spinner spinnerCityCap,spinnercity;
                tvNegativ=dialog.findViewById(R.id.tv_dialog_negative);
                tvPositive=dialog.findViewById(R.id.tv_dialog_positive);
                etName=dialog.findViewById(R.id.Et_name);
                etFamiy=dialog.findViewById(R.id.Et_family);
                etAddress=dialog.findViewById(R.id.Et_address);
                etMobile=dialog.findViewById(R.id.Et_phone);
                etPhone=dialog.findViewById(R.id.Et_phone_home);
                etPostalCode=dialog.findViewById(R.id.Et_postalcod);
                spinnercity =dialog.findViewById(R.id.spinner_city);
                spinnerCityCap = dialog.findViewById(R.id.spiner_city_cap);

                GetListCityCap(spinnerCityCap);

                spinnerCityCap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        idcityCap = arrayIDcityCap.get(i);
                        GetListCity(idcityCap,spinnercity);
                        
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinnercity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        idcity = arrayIDcity.get(i);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });




                tvNegativ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        },500);

                    }
                });

                tvPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                apiServices.AddAddress(token, etName.getText().toString().trim(), etFamiy.getText().toString().trim(), etPostalCode.getText().toString().trim()
                                        , etPhone.getText().toString().trim(),
                                        etMobile.getText().toString().trim(),
                                        idcity,
                                        idcityCap,
                                        etAddress.getText().toString().trim()
                                        , new ApiServices.OnAddressAdd() {
                                            @Override
                                            public void onAddress(String response) {
                                                if (response.equals("218")){
                                                    dialog.dismiss();
                                                    apiServices.GetAddress(token, new ApiServices.OnAddressReceived() {
                                                        @Override
                                                        public void onReceived(List<ModelAddress> modelAddresses) {
                                                            adapterAddress=new AdapterAddress(EditInformation.this,modelAddresses);
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                                                            recyclerView.hasFixedSize();
                                                            recyclerView.setAdapter(adapterAddress);
                                                        }
                                                    });
                                                    Toast.makeText(EditInformation.this, "آدرس با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(EditInformation.this, response, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        },500);

                    }
                });
            }
        });



    }

    public void GetListCityCap(final Spinner spinnerCityCap){


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(1, Links.CITY+"?subid=0", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("city",response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("list-city");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        cityCap.add(jsonObject.getString("cityname"));
                        arrayIDcityCap.add(jsonObject.getString("idcity"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditInformation.this,R.layout.support_simple_spinner_dropdown_item,cityCap);
                    spinnerCityCap.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditInformation.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("city",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(EditInformation.this).addToRequestQueue(jsonArrayRequest);
    }

    public void GetListCity(String id, final Spinner spinnerCity){

        if (!city.isEmpty()){
            city.clear();
        }
        arrayIDcity.clear();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(0, Links.CITY+"?subid="+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("city",response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("list-city");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        city.add(jsonObject.getString("cityname"));
                        arrayIDcity.add(jsonObject.getString("idcity"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditInformation.this,R.layout.support_simple_spinner_dropdown_item,city);
                    spinnerCity.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditInformation.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("city",error.toString());

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(EditInformation.this).addToRequestQueue(jsonArrayRequest);
    }

    private String convertNumberToEnglish(String num) {
        String d = num;
        d = d.replace("۰", "0");
        d = d.replace("۱", "1");
        d = d.replace("۲", "2");
        d = d.replace("٣", "3");
        d = d.replace("٤", "4");
        d = d.replace("۵", "5");
        d = d.replace("٦", "6");
        d = d.replace("٧", "7");
        d = d.replace("۸", "8");
        d = d.replace("۹", "9");

        d = d.replace("۰", "0");
        d = d.replace("۱", "1");
        d = d.replace("۲", "2");
        d = d.replace("۳", "3");
        d = d.replace("۴", "4");
        d = d.replace("۵", "5");
        d = d.replace("۶", "6");
        d = d.replace("۷", "7");
        d = d.replace("۸", "8");
        d = d.replace("۹", "9");

        return d;
    }

    private void findViews() {
        edFamily = findViewById(R.id.ed_edit_family);
        edName = findViewById(R.id.ed_edit_name);
        imgback = findViewById(R.id.img_back_second_toolbar);
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        btnEdit=findViewById(R.id.btn_edit_edit_activity);
        btnAddAddress = findViewById(R.id.btn_add_address);
        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(EditInformation.this);
        username =userSharedPrefrences.getUserName();
        token = userSharedPrefrences.getUserToken();
        recyclerView = findViewById(R.id.recycler_address);
        if (getIntent().getStringExtra(Put.newuser)!=null){
            btnEdit.setText("ثبت اطلاعات");
        } else {
            btnEdit.setText("ویرایش اطلاعات");
        }

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
