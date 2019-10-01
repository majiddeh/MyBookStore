package com.example.mybookstore.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;

public class LoginActivityND extends AppCompatActivity {
    private TextView txtTitle,txtRegister,txtForgot;
    private ImageView imgBackButton;
    private EditText edPass,edphone;
    private CardView cardLogin;
    private CheckBox chkpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        onClicks();

    }

    private void onClicks() {

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityND.this,ForgetPassActivity.class));
            }
        });

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivityND.this, RegisterActivityND.class), Put.REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        chkpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {edPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);}
            }
        });

        cardLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPass.getText().toString().equals("") && edphone.getText().toString().equals("")){
                    Toast.makeText(LoginActivityND.this, "لطفا همه مقادیر را پر کنید", Toast.LENGTH_SHORT).show();
                }else {
                    ApiServices apiServices = new ApiServices(LoginActivityND.this);
                    apiServices.loginUser(edphone.getText().toString(), edPass.getText().toString(), new ApiServices.OnLoginComplete() {
                        @Override
                        public void onResponse(int loginStatus, String image) {
                            switch (loginStatus){
                                case Put.STATUS_FAILED:
                                    Toast.makeText(LoginActivityND.this, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
                                    break;
                                case Put.STATUS_SUCCESS:
                                    Toast.makeText(LoginActivityND.this, "به فروشگاه کتاب صامد خوش آمدید", Toast.LENGTH_SHORT).show();
                                    UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(LoginActivityND.this);
                                    userSharedPrefrences.saveUserLoginInfo(edphone.getText().toString().trim(),image);
                                    Intent intent = new Intent();
                                    intent.putExtra(Put.phone,edphone.getText().toString().trim());
                                    intent.putExtra(Put.image,image);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                    break;
                            }
                        }
                    });

                }

            }
        });



    }

    private void findViews() {

        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("ورود");
        imgBackButton = findViewById(R.id.img_back_second_toolbar);
        txtRegister = findViewById(R.id.txt_register);
        edPass = findViewById(R.id.ed_pass_login);
        edphone = findViewById(R.id.ed_phone_login);
        chkpass = findViewById(R.id.chk_show_pass_login);
        Typeface typeface = Typeface.createFromAsset(getAssets(), Links.LINK_FONT_VAZIR);
        chkpass.setTypeface(typeface);
        cardLogin = findViewById(R.id.card_login);
        txtForgot = findViewById(R.id.txt_forgot_password);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Put.REQUEST_CODE && resultCode == RESULT_OK){
            String phone = data.getStringExtra(Put.phone);
            String password = data.getStringExtra(Put.password);
            edphone.setText(phone);
            edPass.setText(password);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
