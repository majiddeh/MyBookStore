package com.example.mybookstore.Activities;

import android.content.Intent;
import android.media.Image;
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
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;

public class LoginActivity extends AppCompatActivity {
    private TextView txtTitle,txtRegister;
    private EditText edPass,edphone;
    private ImageView imgBackButton;
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

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
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
                ApiServices apiServices = new ApiServices(LoginActivity.this);
                apiServices.loginUser(edphone.getText().toString().trim(), edPass.getText().toString().trim(), new ApiServices.OnLoginComplete() {
                    @Override
                    public void onResponse(int loginStatus) {
                        switch (loginStatus){
                            case Put.STATUS_FAILED:
                                Toast.makeText(LoginActivity.this, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
                                break;
                            case Put.STATUS_SUCCESS:
                                Toast.makeText(LoginActivity.this, "به فروشگاه کتاب صامد خوش آمدید", Toast.LENGTH_SHORT).show();
                                UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(LoginActivity.this);
                                userSharedPrefrences.saveUserLoginInfo(edphone.getText().toString().trim());
                                Intent intent = new Intent();
                                intent.putExtra(Put.phone,edphone.getText().toString().trim());
                                setResult(RESULT_OK,intent);
                                finish();
                                break;
                        }
                    }
                });
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
        cardLogin = findViewById(R.id.card_login);


    }
}
